package org.oxerr.ticketnetwork.client.inventory;

import java.util.List;

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

import com.github.fge.jsonpatch.JsonPatch;

public interface InventoryService {

	TicketGroupsV4GetModel getTicketGroups(TicketGroupQuery q);

	TicketGroupV4GetModel createTicketGroup(TicketGroupV4PostModel ticketGroup);

	TicketGroupV4GetModel getTicketGroup(Integer ticketGroupId);

	void deleteTicketGroup(Integer ticketGroupId);

	TicketGroupV4GetModel updateTicketGroup(
		Integer ticketGroupId,
		JsonPatch patch
	);

	TicketGroupV4GetModel updateTicketGroup(
		Integer ticketGroupId,
		TicketGroup target,
		TicketGroup source
	);

	TicketGroupV4GetModel updateTicketGroup(
		Integer ticketGroupId,
		TicketGroupV4PostModel target,
		TicketGroupV4PostModel source
	);

	TicketGroupsV4GetModel getAllTicketGroups(AllTicketGroupQuery q);

	AdditionalNoteClassesGetModel getAdditionalNoteClasses();

	AdditionalNoteDescriptionsGetModel getAdditionalNotes();

	BroadcastChannelsGetModel getBroadcastChannels();

	List<NearTermShippingMethodGetModel> getNearTermShippingMethods();

	SeatingTypesGetModel getSeatingTypes();

	StockTypesGetModel getStockTypes();

	TicketGroupTypesGetModel getTypes();

}
