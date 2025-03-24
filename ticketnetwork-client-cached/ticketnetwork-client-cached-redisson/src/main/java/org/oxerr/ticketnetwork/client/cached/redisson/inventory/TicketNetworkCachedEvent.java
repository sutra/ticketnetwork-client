package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import java.io.Serializable;
import java.time.OffsetDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.oxerr.ticketnetwork.client.cached.inventory.TicketNetworkEvent;

public class TicketNetworkCachedEvent implements Serializable {

	private static final long serialVersionUID = 2025022601L;

	private String id;

	private Integer marketplaceEventId;

	private OffsetDateTime startDate;

	public TicketNetworkCachedEvent() {
	}

	public TicketNetworkCachedEvent(String id, Integer marketplaceEventId, OffsetDateTime startDate) {
		this.id = id;
		this.marketplaceEventId = marketplaceEventId;
		this.startDate = startDate;
	}

	public TicketNetworkCachedEvent(TicketNetworkEvent event) {
		this(event.getId(), event.getMarketplaceEventId(), event.getStartDate());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getMarketplaceEventId() {
		return marketplaceEventId;
	}

	public void setMarketplaceEventId(Integer marketplaceEventId) {
		this.marketplaceEventId = marketplaceEventId;
	}

	public OffsetDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(OffsetDateTime startDate) {
		this.startDate = startDate;
	}

	public TicketNetworkEvent toTicketNetworkEvent() {
		return new TicketNetworkEvent(id, startDate, marketplaceEventId);
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
		if (!(obj instanceof TicketNetworkCachedEvent)) {
			return false;
		}
		TicketNetworkCachedEvent rhs = (TicketNetworkCachedEvent) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
