package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.ticket.inventory.support.cached.redisson.CachedListing;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;
import org.oxerr.ticketnetwork.client.cached.inventory.TicketNetworkEvent;
import org.oxerr.ticketnetwork.client.cached.inventory.TicketNetworkTicketGroup;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;

public class TicketNetworkCachedTicketGroup extends CachedListing<TicketGroupV4PostModel> {

	private static final long serialVersionUID = 2024101701L;

	private TicketNetworkCachedEvent event;

	private Integer ticketNetworkEventId;

	public TicketNetworkCachedTicketGroup() {
	}

	public TicketNetworkCachedTicketGroup(TicketNetworkEvent event, TicketNetworkTicketGroup ticketGroup, Status status) {
		this(new TicketNetworkCachedEvent(event), ticketGroup, status);
	}

	public TicketNetworkCachedTicketGroup(TicketNetworkCachedEvent event, TicketNetworkTicketGroup ticketGroup, Status status) {
		this(event, ticketGroup.getRequest(), status);
	}

	public TicketNetworkCachedTicketGroup(TicketNetworkCachedEvent event, TicketGroupV4PostModel request, Status status) {
		super(request, status);
		this.event = event;
		this.ticketNetworkEventId = event.getTicketNetworkEventId();
	}

	public TicketNetworkCachedEvent getEvent() {
		return event;
	}

	public void setEvent(TicketNetworkCachedEvent event) {
		this.event = event;
	}

	public Integer getTicketNetworkEventId() {
		return ticketNetworkEventId;
	}

	public void setTicketNetworkEventId(Integer ticketNetworkEventId) {
		this.ticketNetworkEventId = ticketNetworkEventId;
	}

	public TicketNetworkTicketGroup toTicketNetworkTicketGroup() {
		return new TicketNetworkTicketGroup(this.getRequest().getReferenceTicketGroupId(), this.event.getTicketNetworkEventId(), this.getRequest());
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
