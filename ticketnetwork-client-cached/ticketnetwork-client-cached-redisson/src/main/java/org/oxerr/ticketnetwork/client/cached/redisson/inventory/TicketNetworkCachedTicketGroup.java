package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.ticket.inventory.support.cached.redisson.CachedListing;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;
import org.oxerr.ticketnetwork.client.cached.inventory.TicketNetworkEvent;
import org.oxerr.ticketnetwork.client.cached.inventory.TicketNetworkTicketGroup;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4GetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;

public class TicketNetworkCachedTicketGroup extends CachedListing<TicketGroupV4PostModel> {

	private static final long serialVersionUID = 2024101701L;

	private String id;

	private TicketNetworkCachedEvent event;

	/**
	 * The local ID of the ticket group.
	 *
	 * @see {@link TicketGroupV4GetModel#getTicketGroupId()}
	 */
	private Integer ticketGroupId;

	public TicketNetworkCachedTicketGroup() {
	}

	public TicketNetworkCachedTicketGroup(
		TicketNetworkEvent event,
		TicketNetworkTicketGroup ticketGroup,
		Status status
	) {
		this(new TicketNetworkCachedEvent(event), ticketGroup, status);
	}

	public TicketNetworkCachedTicketGroup(
		TicketNetworkCachedEvent event,
		TicketNetworkTicketGroup ticketGroup,
		Status status
	) {
		this(
			event,
			ticketGroup.getId(),
			ticketGroup.getRequest(),
			ticketGroup.getTicketGroupId(),
			status
		);
	}

	public TicketNetworkCachedTicketGroup(
		TicketNetworkCachedEvent event,
		String id,
		TicketGroupV4PostModel request,
		Integer ticketGroupId,
		Status status
	) {
		super(request, status);
		this.id = id;
		this.event = event;
		this.ticketGroupId = ticketGroupId;
	}

	public TicketNetworkCachedEvent getEvent() {
		return event;
	}

	public void setEvent(TicketNetworkCachedEvent event) {
		this.event = event;
	}

	public Integer getTicketGroupId() {
		return ticketGroupId;
	}

	public void setTicketGroupId(Integer ticketGroupId) {
		this.ticketGroupId = ticketGroupId;
	}

	public TicketNetworkTicketGroup toTicketNetworkTicketGroup() {
		return new TicketNetworkTicketGroup(
			id,
			this.getRequest(),
			this.event.getTicketNetworkEventId(),
			ticketGroupId
		);
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
