package org.oxerr.ticketnetwork.client.cached.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.ticket.inventory.support.Listing;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;

public class TicketNetworkTicketGroup extends Listing<Integer, TicketGroupV4PostModel> {

	private static final long serialVersionUID = 2024101701L;

	private Integer ticketNetworkEventId;

	public TicketNetworkTicketGroup() {
	}

	public TicketNetworkTicketGroup(Integer id, Integer ticketNetworkEventId, TicketGroupV4PostModel request) {
		super(id, request);
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
