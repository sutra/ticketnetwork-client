package org.oxerr.ticketnetwork.client.rescu.impl.inventory;

import java.io.IOException;

import org.oxerr.ticketnetwork.client.inventory.InventoryService;
import org.oxerr.ticketnetwork.client.model.SeatingTypesGetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupTypesGetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4GetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupsV4GetModel;
import org.oxerr.ticketnetwork.client.rescu.resource.inventory.InventoryResource;

public class InventoryServiceImpl implements InventoryService {

	private final InventoryResource inventoryResource;

	public InventoryServiceImpl(InventoryResource inventoryResource) {
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
	public TicketGroupV4GetModel createTicketGroup(TicketGroupV4PostModel ticketGroup) throws IOException {
		return inventoryResource.createTicketGroup(ticketGroup);
	}

	@Override
	public void deleteTicketGroup(Integer ticketGroupId) throws IOException {
		inventoryResource.deleteTicketGroup(ticketGroupId);
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
