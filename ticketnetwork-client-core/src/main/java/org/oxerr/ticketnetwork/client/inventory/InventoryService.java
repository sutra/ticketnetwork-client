package org.oxerr.ticketnetwork.client.inventory;

import org.oxerr.ticketnetwork.client.model.response.inventory.TicketGroupsV4GetModel;

public interface InventoryService {

	TicketGroupsV4GetModel getTicketGroups(
		Boolean hasEticket,
		Boolean pending,
		Boolean returnTicketsData,
		Integer perPage,
		Integer page,
		Integer skip,
		String filter,
		String orderby
	);

}
