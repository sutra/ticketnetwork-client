package org.oxerr.ticketnetwork.client.rescu.impl.inventory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.diff.JsonDiff;

class JsonPatchTest {

	private final Logger log = LogManager.getLogger();

	@Test
	void testDiff() throws JsonProcessingException, IllegalArgumentException, JsonPatchException {
		ObjectMapper objectMapper = new ObjectMapper();

		TicketGroupV4PostModel source = new TicketGroupV4PostModel();
		TicketGroupV4PostModel target = new TicketGroupV4PostModel();

		// Modify target to simulate a change
		target.setQuantity(2);
		target.setTicketGroupTypeId(2);

		// Convert to JsonNode
		JsonNode sourceNode = objectMapper.valueToTree(source);
		JsonNode targetNode = objectMapper.valueToTree(target);

		// Generate JSON Patch
		JsonPatch patch = JsonDiff.asJsonPatch(sourceNode, targetNode);
		log.info("Patch: {}", patch);

		log.info("Patch: {}", objectMapper.writeValueAsString(patch));

		// Apply Patch
		JsonNode patchedNode = patch.apply(sourceNode);
		TicketGroupV4PostModel patchedObject = objectMapper.treeToValue(patchedNode, TicketGroupV4PostModel.class);
		log.info("Patched Object: {}", patchedObject);
	}

}
