package org.oxerr.ticketnetwork.client.inventory;

import org.oxerr.ticketnetwork.client.model.TicketGroupV4GetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupsV4GetModel;

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

	TicketGroupV4GetModel createTicketGroup(TicketGroupV4PostModel ticketGroup);

	void deleteTicketGroup(Integer ticketGroupId);

}
