package org.oxerr.ticketnetwork.client.inventory;

import java.io.IOException;

import org.oxerr.ticketnetwork.client.model.SeatingTypesGetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupTypesGetModel;
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
	) throws IOException;

	TicketGroupV4GetModel createTicketGroup(TicketGroupV4PostModel ticketGroup) throws IOException;

	void deleteTicketGroup(Integer ticketGroupId) throws IOException;

	SeatingTypesGetModel getSeatingTypes() throws IOException;

	TicketGroupTypesGetModel getTypes() throws IOException;

}
