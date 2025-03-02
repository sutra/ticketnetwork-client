package org.oxerr.ticketnetwork.client.cached.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.ticket.inventory.support.Listing;
import org.oxerr.ticketnetwork.client.model.Event;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4GetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;

public class TicketNetworkTicketGroup extends Listing<String, TicketGroupV4PostModel> {

	private static final long serialVersionUID = 2024101701L;

	/**
	 * The ID of the ticket network event.
	 *
	 * @see {@link Event#getId()}
	 */
	private Integer ticketNetworkEventId;

	/**
	 * The reference (external) ticket group ID.
	 *
	 * @see {@link TicketGroupV4PostModel#getReferenceTicketGroupId()}
	 */
	private Integer referenceTicketGroupId;

	/**
	 * The local ID of the ticket group.
	 *
	 * @see {@link TicketGroupV4GetModel#getTicketGroupId()}
	 */
	private Integer ticketGroupId;

	public TicketNetworkTicketGroup() {
	}

	public TicketNetworkTicketGroup(
		String id,
		TicketGroupV4PostModel request,
		Integer ticketNetworkEventId
	) {
		this(id, request, ticketNetworkEventId, null);
	}

	public TicketNetworkTicketGroup(
		String id,
		TicketGroupV4PostModel request,
		Integer ticketNetworkEventId,
		Integer ticketGroupId
	) {
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
