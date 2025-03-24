package org.oxerr.ticketnetwork.client.cached.inventory;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.oxerr.ticket.inventory.support.Event;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;

public class TicketNetworkEvent extends Event<String, String, TicketGroupV4PostModel, TicketNetworkListing> {

	private static final long serialVersionUID = 2023031901L;

	private Integer marketplaceEventId;

	public TicketNetworkEvent() {
		this(null, null, null, Collections.emptyList());
	}

	public TicketNetworkEvent(String id, OffsetDateTime startDate, Integer marketplaceEventId) {
		this(id, startDate, marketplaceEventId, Collections.emptyList());
	}

	public TicketNetworkEvent(String id, OffsetDateTime startDate, Integer marketplaceEventId, List<TicketNetworkListing> listings) {
		super(id, startDate, listings);
		this.marketplaceEventId = marketplaceEventId;
	}

	public Integer getMarketplaceEventId() {
		return marketplaceEventId;
	}

	public void setMarketplaceEventId(Integer marketplaceEventId) {
		this.marketplaceEventId = marketplaceEventId;
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
		if (!(obj instanceof TicketNetworkEvent)) {
			return false;
		}
		TicketNetworkEvent rhs = (TicketNetworkEvent) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
