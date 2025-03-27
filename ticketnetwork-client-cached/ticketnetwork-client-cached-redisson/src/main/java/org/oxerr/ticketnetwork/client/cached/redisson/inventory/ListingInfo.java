package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.oxerr.ticketnetwork.client.model.TicketGroup;

class ListingInfo {

	private final Integer marketplaceEventId;

	private final Integer referenceTicketGroupId;

	public ListingInfo(TicketNetworkCachedListing c) {
		this.marketplaceEventId = c.getEvent().getMarketplaceEventId();
		this.referenceTicketGroupId = c.getRequest().getReferenceTicketGroupId();
	}

	public ListingInfo(TicketGroup listing) {
		this.marketplaceEventId = listing.getEvent().getId();
		this.referenceTicketGroupId = listing.getReferenceTicketGroupId();
	}

	public Integer getMarketplaceEventId() {
		return marketplaceEventId;
	}

	public Integer getReferenceTicketGroupId() {
		return referenceTicketGroupId;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ListingInfo)) {
			return false;
		}
		ListingInfo rhs = (ListingInfo) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
