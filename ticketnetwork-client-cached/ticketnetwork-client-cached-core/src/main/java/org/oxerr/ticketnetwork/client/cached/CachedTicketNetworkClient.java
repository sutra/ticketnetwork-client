package org.oxerr.ticketnetwork.client.cached;

import org.oxerr.ticketnetwork.client.TicketNetworkClient;
import org.oxerr.ticketnetwork.client.cached.inventory.TicketNetworkCachedListingService;

public interface CachedTicketNetworkClient {

	TicketNetworkClient getClient();

	TicketNetworkCachedListingService getCachedListingService();

}
