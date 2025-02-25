package org.oxerr.ticketnetwork.client.cached;

import org.oxerr.ticketnetwork.client.TicketNetworkClient;
import org.oxerr.ticketnetwork.client.cached.inventory.TicketNetworkCachedTicketGroupService;

public interface CachedTicketNetworkClient {

	TicketNetworkClient getClient();

	TicketNetworkCachedTicketGroupService getCachedListingService();

}
