package org.oxerr.ticketnetwork.client.rescu.impl.inventory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.oxerr.ticketnetwork.client.model.TicketGroup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.diff.JsonDiff;

class TicketGroupPatcher {

	private static final String FACE_PRICE_PATH = "/unitPrice/facePrice/value";
	private static final String FACE_CURRENCY_CODE_PATH = "/unitPrice/facePrice/currencyCode";

	private final Logger log = LogManager.getLogger();

	private final ObjectMapper objectMapper;

	public TicketGroupPatcher(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public JsonPatch createPatch(TicketGroup target, TicketGroup source) {
		JsonPatch patch = diff(target, source);
		log.debug("[createPatch] source: {}, target: {}, patch: {}", source, target, patch);
		return patch;
	}

	private JsonPatch diff(TicketGroup target, TicketGroup source) {
		JsonNode targetNode = objectMapper.valueToTree(target);
		JsonNode sourceNode = objectMapper.valueToTree(source);

		ArrayNode patchArray = objectMapper.valueToTree(JsonDiff.asJsonPatch(sourceNode, targetNode));
		ensureFieldPairConsistency(patchArray, target);

		return toJsonPatch(patchArray);
	}

	private JsonPatch toJsonPatch(ArrayNode patchArray) {
		try {
			return objectMapper.treeToValue(patchArray, JsonPatch.class);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Failed to convert patch array to JsonPatch", e);
		}
	}

	// Face price and face currency code cannot have different operation types.
	// If contain face price, append face currency code.
	private void ensureFieldPairConsistency(ArrayNode patchArray, TicketGroup target) {
		String facePriceOp = null;
		String faceCurrencyOp = null;

		for (JsonNode op : patchArray) {
			String path = op.path("path").asText();
			String opType = op.path("op").asText();

			if (FACE_PRICE_PATH.equals(path)) {
				facePriceOp = opType;
			} else if (FACE_CURRENCY_CODE_PATH.equals(path)) {
				faceCurrencyOp = opType;
			}
		}

		if (facePriceOp != null && faceCurrencyOp == null) {
			addMatchingOp(patchArray, facePriceOp, FACE_CURRENCY_CODE_PATH,
				target.getUnitPrice().getFacePrice().getCurrencyCode());
		} else if (faceCurrencyOp != null && facePriceOp == null) {
			addMatchingOp(patchArray, faceCurrencyOp, FACE_PRICE_PATH,
				target.getUnitPrice().getFacePrice().getValue());
		}
	}

	private void addMatchingOp(ArrayNode patchArray, String opType, String path, Object value) {
		ObjectNode newOp = objectMapper.createObjectNode();
		newOp.put("op", opType);
		newOp.put("path", path);

		if (!"remove".equals(opType) && value != null) {
			newOp.set("value", objectMapper.valueToTree(value));
		}

		patchArray.add(newOp);
	}
}
