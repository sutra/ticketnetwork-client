package org.oxerr.ticketnetwork.client.inventory;

import java.io.IOException;

import org.oxerr.ticketnetwork.client.model.SeatingTypesGetModel;
import org.oxerr.ticketnetwork.client.model.StockTypesGetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroup;
import org.oxerr.ticketnetwork.client.model.TicketGroupTypesGetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4GetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupsV4GetModel;

import com.github.fge.jsonpatch.JsonPatch;

public interface InventoryService {

	TicketGroupsV4GetModel getTicketGroups(TicketGroupQuery q) throws IOException;

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
		TicketGroup target,
		TicketGroup source
	) throws IOException;

	TicketGroupV4GetModel updateTicketGroup(
		Integer ticketGroupId,
		TicketGroupV4PostModel target,
		TicketGroupV4PostModel source
	) throws IOException;

	TicketGroupsV4GetModel getAllTicketGroups(AllTicketGroupQuery q) throws IOException;

	SeatingTypesGetModel getSeatingTypes() throws IOException;

	StockTypesGetModel getStockTypes() throws IOException;

	TicketGroupTypesGetModel getTypes() throws IOException;

}
