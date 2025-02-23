package org.oxerr.ticketnetwork.client.rescu.impl.inventory;

import org.oxerr.ticketnetwork.client.inventory.InventoryService;
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
	) {
		return inventoryResource.getTicketGroups(
			hasEticket, pending, returnTicketsData, perPage, page, skip,
			filter, orderby
		);
	}

	@Override
	public TicketGroupV4GetModel createTicketGroup(TicketGroupV4PostModel ticketGroup) {
		return inventoryResource.createTicketGroup(ticketGroup);
	}

}
