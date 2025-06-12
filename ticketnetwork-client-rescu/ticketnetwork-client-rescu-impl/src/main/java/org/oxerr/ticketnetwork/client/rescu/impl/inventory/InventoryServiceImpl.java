package org.oxerr.ticketnetwork.client.rescu.impl.inventory;

import java.util.List;
import java.util.Optional;

import org.oxerr.ticketnetwork.client.inventory.AllTicketGroupQuery;
import org.oxerr.ticketnetwork.client.inventory.InventoryService;
import org.oxerr.ticketnetwork.client.inventory.TicketGroupQuery;
import org.oxerr.ticketnetwork.client.model.AdditionalNoteClassesGetModel;
import org.oxerr.ticketnetwork.client.model.AdditionalNoteDescriptionsGetModel;
import org.oxerr.ticketnetwork.client.model.BroadcastChannelsGetModel;
import org.oxerr.ticketnetwork.client.model.NearTermShippingMethodGetModel;
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
	public TicketGroupsV4GetModel getTicketGroups(TicketGroupQuery q) {
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
	) {
		return inventoryResource.createTicketGroup(ticketGroup);
	}

	@Override
	public TicketGroupV4GetModel getTicketGroup(Integer ticketGroupId) {
		return inventoryResource.getTicketGroup(ticketGroupId);
	}

	@Override
	public void deleteTicketGroup(Integer ticketGroupId) {
		inventoryResource.deleteTicketGroup(ticketGroupId);
	}

	@Override
	public TicketGroupV4GetModel updateTicketGroup(
		Integer ticketGroupId,
		JsonPatch patch
	) {
		return inventoryResource.updateTicketGroup(ticketGroupId, patch);
	}

	@Override
	public TicketGroupV4GetModel updateTicketGroup(
		Integer ticketGroupId,
		TicketGroup target,
		TicketGroup source
	) {
		JsonPatch patch = this.ticketGroupPatcher.createPatch(target, source);
		return updateTicketGroup(ticketGroupId, patch);
	}

	@Override
	public TicketGroupV4GetModel updateTicketGroup(
		Integer ticketGroupId,
		TicketGroupV4PostModel target,
		TicketGroupV4PostModel source
	) {
		TicketGroupV4GetModel targetTicketGroupV4GetModel = new TicketGroupV4GetModel(target);
		TicketGroupV4GetModel sourceTicketGroupV4GetModel = new TicketGroupV4GetModel(source);
		return updateTicketGroup(ticketGroupId, targetTicketGroupV4GetModel, sourceTicketGroupV4GetModel);
	}

	@Override
	public TicketGroupsV4GetModel getAllTicketGroups(AllTicketGroupQuery q) {
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
	public AdditionalNoteClassesGetModel getAdditionalNoteClasses() {
		return inventoryResource.getAdditionalNoteClasses();
	}

	@Override
	public AdditionalNoteDescriptionsGetModel getAdditionalNotes() {
		return inventoryResource.getAdditionalNotes();
	}

	@Override
	public BroadcastChannelsGetModel getBroadcastChannels() {
		return inventoryResource.getBroadcastChannels();
	}

	@Override
	public List<NearTermShippingMethodGetModel> getNearTermShippingMethods() {
		return inventoryResource.getNearTermShippingMethods();
	}

	@Override
	public SeatingTypesGetModel getSeatingTypes() {
		return inventoryResource.getSeatingTypes();
	}

	@Override
	public StockTypesGetModel getStockTypes() {
		return inventoryResource.getStockTypes();
	}

	@Override
	public TicketGroupTypesGetModel getTypes() {
		return inventoryResource.getTypes();
	}

}
