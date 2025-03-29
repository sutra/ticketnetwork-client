package org.oxerr.ticketnetwork.client.rescu.impl.inventory;

import java.io.IOException;
import java.util.Optional;

import org.oxerr.ticketnetwork.client.inventory.AllTicketGroupQuery;
import org.oxerr.ticketnetwork.client.inventory.InventoryService;
import org.oxerr.ticketnetwork.client.inventory.TicketGroupQuery;
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
	public TicketGroupsV4GetModel getTicketGroups(TicketGroupQuery q) throws IOException {
		return inventoryResource.getTicketGroups(
			q.getHasEticket(),
			q.getPending(),
			q.getReturnTicketsData(),
			q.getPerPage(),
			q.getPage(),
			Optional.ofNullable(q.getSkip()).filter(skip -> skip > 0).orElse(null),
			q.getFilter(),
			q.getOrderby()
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
		TicketGroupV4PostModel target,
		TicketGroupV4PostModel source
	) throws IOException {
		TicketGroupV4GetModel targetTicketGroupV4GetModel = new TicketGroupV4GetModel(target);
		TicketGroupV4GetModel sourceTicketGroupV4GetModel = new TicketGroupV4GetModel(source);

		// Convert to JsonNode
		JsonNode targetNode = objectMapper.valueToTree(targetTicketGroupV4GetModel);
		JsonNode sourceNode = objectMapper.valueToTree(sourceTicketGroupV4GetModel);

		// Generate JSON Patch
		JsonPatch patch = JsonDiff.asJsonPatch(sourceNode, targetNode);
		log.debug("ticketGroupId: {}, sourceNode: {}, targetNode: {}, patch: {}",
			ticketGroupId, sourceNode, targetNode, patch);

		return updateTicketGroup(ticketGroupId, patch);
	}

	@Override
	public TicketGroupsV4GetModel getAllTicketGroups(AllTicketGroupQuery q) throws IOException {
		return inventoryResource.getAllTicketGroups(
			q.getHasEticket(),
			q.getPending(),
			q.getIncludeTicketNetworkInventory(),
			q.getReturnTicketsData(),
			q.getPerPage(),
			q.getPage(),
			q.getSkip(),
			q.getFilter(),
			q.getOrderby()
		);
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
