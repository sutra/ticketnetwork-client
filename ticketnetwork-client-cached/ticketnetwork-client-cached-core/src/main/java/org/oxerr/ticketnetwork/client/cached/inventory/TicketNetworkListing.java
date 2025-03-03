package org.oxerr.ticketnetwork.client.cached.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.ticket.inventory.support.Listing;
import org.oxerr.ticketnetwork.client.model.Event;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4GetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;

public class TicketNetworkListing extends Listing<String, TicketGroupV4PostModel> {

	private static final long serialVersionUID = 2024101701L;

	/**
	 * The ID of the ticket network event.
	 *
	 * @see Event#getId()
	 */
	private Integer ticketNetworkEventId;

	/**
	 * The reference (external) ticket group ID.
	 *
	 * @see TicketGroupV4PostModel#getReferenceTicketGroupId()
	 */
	private Integer referenceTicketGroupId;

	/**
	 * The local ID of the ticket group.
	 *
	 * @see TicketGroupV4GetModel#getTicketGroupId()
	 */
	private Integer ticketGroupId;

	public TicketNetworkListing() {
	}

	public TicketNetworkListing(String id, Integer ticketNetworkEventId, TicketGroupV4PostModel request) {
		this(id, ticketNetworkEventId, request, null);
	}

	public TicketNetworkListing(String id, Integer ticketNetworkEventId, TicketGroupV4PostModel request, Integer ticketGroupId) {
		super(id, request);
		this.ticketNetworkEventId = ticketNetworkEventId;
		this.referenceTicketGroupId = request.getReferenceTicketGroupId();
		this.ticketGroupId = ticketGroupId;
	}

	public Integer getTicketNetworkEventId() {
		return ticketNetworkEventId;
	}

	public void setTicketNetworkEventId(Integer ticketNetworkEventId) {
		this.ticketNetworkEventId = ticketNetworkEventId;
	}

	public Integer getReferenceTicketGroupId() {
		return referenceTicketGroupId;
	}

	public void setReferenceTicketGroupId(Integer referenceTicketGroupId) {
		this.referenceTicketGroupId = referenceTicketGroupId;
	}

	public Integer getTicketGroupId() {
		return ticketGroupId;
	}

	public void setTicketGroupId(Integer ticketGroupId) {
		this.ticketGroupId = ticketGroupId;
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
