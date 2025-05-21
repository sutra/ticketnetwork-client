package org.oxerr.ticketnetwork.client.cached.inventory;

import org.oxerr.ticket.inventory.support.cached.CachedListingService;
import org.oxerr.ticketnetwork.client.model.TicketGroup;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;

public interface TicketNetworkCachedListingService
	extends CachedListingService<String, String, TicketGroupV4PostModel, TicketNetworkListing, TicketNetworkEvent> {

	default void updateCache(TicketNetworkEvent event, TicketNetworkListing listing, TicketGroup ticketGroup) {
		updateCache(event.getId(), listing.getId(), ticketGroup.getTicketGroupId());
	}

	void updateCache(String eventId, String listingId, Integer ticketGroupId);

	void check();

	void check(CheckOptions options);

}
