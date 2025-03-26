package org.oxerr.ticketnetwork.client.cached.inventory;

import org.oxerr.ticket.inventory.support.cached.CachedListingService;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;

public interface TicketNetworkCachedListingService
	extends CachedListingService<String, String, TicketGroupV4PostModel, TicketNetworkListing, TicketNetworkEvent> {

	void check();

	void check(CheckOptions options);

}
