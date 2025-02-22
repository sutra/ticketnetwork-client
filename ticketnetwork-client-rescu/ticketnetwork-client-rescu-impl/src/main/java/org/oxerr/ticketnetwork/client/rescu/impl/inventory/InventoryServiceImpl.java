package org.oxerr.ticketnetwork.client.rescu.impl.inventory;

import org.oxerr.ticketnetwork.client.inventory.InventoryService;
import org.oxerr.ticketnetwork.client.model.response.inventory.TicketGroupsV4GetModel;
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
		return this.inventoryResource.getTicketGroups(
			hasEticket, pending, returnTicketsData, perPage, page, skip,
			filter, orderby
		);
	}

}
