package org.oxerr.ticketnetwork.client.cached.inventory;

import org.oxerr.ticket.inventory.support.cached.CachedListingService;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;

public interface TicketNetworkCachedTicketGroupService
	extends CachedListingService<String, String, TicketGroupV4PostModel, TicketNetworkTicketGroup, TicketNetworkEvent> {
}
