package org.oxerr.ticketnetwork.client.cached.inventory;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.ticket.inventory.support.Event;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;

public class TicketNetworkEvent extends Event<String, String, TicketGroupV4PostModel, TicketNetworkListing> {

	private static final long serialVersionUID = 2023031901L;

	private Integer ticketNetworkEventId;

	public TicketNetworkEvent() {
		this(null, null, null, Collections.emptyList());
	}

	public TicketNetworkEvent(String id, OffsetDateTime startDate, Integer ticketNetworkEventId) {
		this(id, startDate, ticketNetworkEventId, Collections.emptyList());
	}

	public TicketNetworkEvent(String id, OffsetDateTime startDate, Integer ticketNetworkEventId, List<TicketNetworkListing> listings) {
		super(id, startDate, listings);
		this.ticketNetworkEventId = ticketNetworkEventId;
	}

	public Integer getTicketNetworkEventId() {
		return ticketNetworkEventId;
	}

	public void setTicketNetworkEventId(Integer ticketNetworkEventId) {
		this.ticketNetworkEventId = ticketNetworkEventId;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

}
