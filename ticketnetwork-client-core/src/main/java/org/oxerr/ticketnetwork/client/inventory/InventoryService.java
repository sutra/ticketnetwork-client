package org.oxerr.ticketnetwork.client.inventory;

import java.io.IOException;

import org.oxerr.ticketnetwork.client.model.SeatingTypesGetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupTypesGetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4GetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupsV4GetModel;

import com.github.fge.jsonpatch.JsonPatch;

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

	TicketGroupV4GetModel createTicketGroup(TicketGroupV4PostModel ticketGroup)
		throws IOException;

	TicketGroupV4GetModel getTicketGroup(Integer ticketGroupId) throws IOException;

	void deleteTicketGroup(Integer ticketGroupId) throws IOException;

	TicketGroupV4GetModel updateTicketGroup(
		Integer ticketGroupId,
		JsonPatch patch
	) throws IOException;

	TicketGroupV4GetModel updateTicketGroup(
		Integer ticketGroupId,
		TicketGroupV4PostModel ticketGroup,
		TicketGroupV4PostModel oldTicketGroup
	) throws IOException;

	SeatingTypesGetModel getSeatingTypes() throws IOException;

	TicketGroupTypesGetModel getTypes() throws IOException;

}
