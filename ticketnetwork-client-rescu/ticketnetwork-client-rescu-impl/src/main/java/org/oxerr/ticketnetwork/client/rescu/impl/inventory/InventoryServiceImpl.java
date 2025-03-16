package org.oxerr.ticketnetwork.client.rescu.impl.inventory;

import java.io.IOException;

import org.oxerr.ticketnetwork.client.inventory.InventoryService;
import org.oxerr.ticketnetwork.client.model.SeatingTypesGetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupTypesGetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4GetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupsV4GetModel;
import org.oxerr.ticketnetwork.client.rescu.resource.inventory.InventoryResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.diff.JsonDiff;

public class InventoryServiceImpl implements InventoryService {

	private final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);

	private final ObjectMapper objectMapper;

	private final InventoryResource inventoryResource;

	public InventoryServiceImpl(ObjectMapper objectMapper, InventoryResource inventoryResource) {
		this.objectMapper = objectMapper;
		this.inventoryResource = inventoryResource;
	}

	@Override
	public TicketGroupsV4GetModel getTicketGroups(
		Boolean hasEticket,
		Boolean pending,
		Boolean returnTicketsData,
		Integer perPage,
		Integer page,
		Integer skip,
		String filter,
		String orderby
	) throws IOException {
		return inventoryResource.getTicketGroups(
			hasEticket, pending, returnTicketsData, perPage, page, skip,
			filter, orderby
		);
	}

	@Override
	public TicketGroupV4GetModel createTicketGroup(
		TicketGroupV4PostModel ticketGroup
	) throws IOException {
		return inventoryResource.createTicketGroup(ticketGroup);
	}

	@Override
	public TicketGroupV4GetModel getTicketGroup(Integer ticketGroupId)
		throws IOException {
		return inventoryResource.getTicketGroup(ticketGroupId);
	}

	@Override
	public void deleteTicketGroup(Integer ticketGroupId) throws IOException {
		inventoryResource.deleteTicketGroup(ticketGroupId);
	}

	@Override
	public TicketGroupV4GetModel updateTicketGroup(
		Integer ticketGroupId,
		JsonPatch patch
	) throws IOException {
		return inventoryResource.updateTicketGroup(ticketGroupId, patch);
	}

	@Override
	public TicketGroupV4GetModel updateTicketGroup(
		Integer ticketGroupId,
		TicketGroupV4PostModel source,
		TicketGroupV4PostModel target
	) throws IOException {
		// Convert to JsonNode
		JsonNode sourceNode = objectMapper.valueToTree(source);
		JsonNode targetNode = objectMapper.valueToTree(target);

		// Generate JSON Patch
		JsonPatch patch = JsonDiff.asJsonPatch(sourceNode, targetNode);
		log.debug("Patch: {}", patch);

		return updateTicketGroup(ticketGroupId, patch);
	}

	@Override
	public SeatingTypesGetModel getSeatingTypes() throws IOException {
		return inventoryResource.getSeatingTypes();
	}

	@Override
	public TicketGroupTypesGetModel getTypes() throws IOException {
		return inventoryResource.getTypes();
	}

}
