package org.oxerr.ticketnetwork.client.rescu.impl.inventory;

import java.io.IOException;
import java.util.Optional;

import org.oxerr.ticketnetwork.client.inventory.AllTicketGroupQuery;
import org.oxerr.ticketnetwork.client.inventory.InventoryService;
import org.oxerr.ticketnetwork.client.inventory.TicketGroupQuery;
import org.oxerr.ticketnetwork.client.model.SeatingTypesGetModel;
import org.oxerr.ticketnetwork.client.model.StockTypesGetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroup;
import org.oxerr.ticketnetwork.client.model.TicketGroupTypesGetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4GetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupsV4GetModel;
import org.oxerr.ticketnetwork.client.rescu.resource.inventory.InventoryResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;

public class InventoryServiceImpl implements InventoryService {

	private final TicketGroupPatcher ticketGroupPatcher;

	private final InventoryResource inventoryResource;

	public InventoryServiceImpl(ObjectMapper objectMapper, InventoryResource inventoryResource) {
		this.ticketGroupPatcher = new TicketGroupPatcher(objectMapper);
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
		TicketGroup target,
		TicketGroup source
	) throws IOException {
		JsonPatch patch = this.ticketGroupPatcher.createPatch(target, source);
		return updateTicketGroup(ticketGroupId, patch);
	}

	@Override
	public TicketGroupV4GetModel updateTicketGroup(
		Integer ticketGroupId,
		TicketGroupV4PostModel target,
		TicketGroupV4PostModel source
	) throws IOException {
		TicketGroupV4GetModel targetTicketGroupV4GetModel = new TicketGroupV4GetModel(target);
		TicketGroupV4GetModel sourceTicketGroupV4GetModel = new TicketGroupV4GetModel(source);
		return updateTicketGroup(ticketGroupId, targetTicketGroupV4GetModel, sourceTicketGroupV4GetModel);
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
	public StockTypesGetModel getStockTypes() throws IOException {
		return inventoryResource.getStockTypes();
	}

	@Override
	public TicketGroupTypesGetModel getTypes() throws IOException {
		return inventoryResource.getTypes();
	}

}
