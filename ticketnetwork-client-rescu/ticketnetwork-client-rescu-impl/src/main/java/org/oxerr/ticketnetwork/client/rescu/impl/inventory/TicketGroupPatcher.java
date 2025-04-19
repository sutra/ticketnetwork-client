package org.oxerr.ticketnetwork.client.rescu.impl.inventory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.oxerr.ticketnetwork.client.model.TicketGroup;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.diff.JsonDiff;

class TicketGroupPatcher {

	private final Logger log = LogManager.getLogger();

	private final ObjectMapper objectMapper;

	public TicketGroupPatcher(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public JsonPatch createPatch(TicketGroup target, TicketGroup source) {
		// Convert to JsonNode
		JsonNode targetNode = objectMapper.valueToTree(target);
		JsonNode sourceNode = objectMapper.valueToTree(source);

		// Generate JSON Patch
		JsonPatch patch = JsonDiff.asJsonPatch(sourceNode, targetNode);

		log.debug("[createPatch] source: {}, target: {}, patch: {}", sourceNode, targetNode, patch);

		return patch;
	}

}
