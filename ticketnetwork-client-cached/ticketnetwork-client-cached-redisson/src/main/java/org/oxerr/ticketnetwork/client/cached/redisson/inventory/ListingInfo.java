package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.oxerr.ticketnetwork.client.model.TicketGroup;

class ListingInfo {

	private final Integer marketplaceEventId;

	private final Integer referenceTicketGroupId;

	private String section;

	private String row;

	private String lowSeat;

	public ListingInfo(TicketNetworkCachedListing c) {
		this.marketplaceEventId = c.getEvent().getMarketplaceEventId();
		this.referenceTicketGroupId = c.getRequest().getReferenceTicketGroupId();
		this.section = c.getRequest().getSection();
		this.row = c.getRequest().getRow();
		this.lowSeat = c.getRequest().getLowSeat();
	}

	public ListingInfo(TicketGroup listing) {
		this.marketplaceEventId = listing.getEvent().getId();
		this.referenceTicketGroupId = listing.getReferenceTicketGroupId();
		this.section = listing.getSeats().getSection();
		this.row = listing.getSeats().getRow();
		this.lowSeat = listing.getSeats().getLowSeat().toString();
	}

	public Integer getMarketplaceEventId() {
		return marketplaceEventId;
	}

	public Integer getReferenceTicketGroupId() {
		return referenceTicketGroupId;
	}

	public String getSection() {
		return section;
	}

	public String getRow() {
		return row;
	}

	public String getLowSeat() {
		return lowSeat;
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
