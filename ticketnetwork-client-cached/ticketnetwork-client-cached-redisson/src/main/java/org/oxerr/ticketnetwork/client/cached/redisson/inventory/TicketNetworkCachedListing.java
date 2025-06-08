package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.oxerr.ticket.inventory.support.cached.redisson.CachedListing;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;
import org.oxerr.ticketnetwork.client.cached.inventory.TicketNetworkEvent;
import org.oxerr.ticketnetwork.client.cached.inventory.TicketNetworkListing;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4GetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;

public class TicketNetworkCachedListing extends CachedListing<TicketGroupV4PostModel> {

	private static final long serialVersionUID = 2024101701L;

	private String id;

	private TicketNetworkCachedEvent event;

	/**
	 * The local ID of the ticket group.
	 *
	 * @see TicketGroupV4GetModel#getTicketGroupId()
	 */
	private Integer ticketGroupId;

	public TicketNetworkCachedListing() {
	}

	public TicketNetworkCachedListing(TicketNetworkEvent event, TicketNetworkListing listing, Status status) {
		this(new TicketNetworkCachedEvent(event), listing, status);
	}

	public TicketNetworkCachedListing(TicketNetworkCachedEvent event, TicketNetworkListing listing, Status status) {
		this(event, listing.getRequest(), listing.getId(), listing.getTicketGroupId(), status);
	}

	public TicketNetworkCachedListing(TicketNetworkCachedEvent event, TicketGroupV4PostModel request, String id, Integer ticketGroupId, Status status) {
		super(request, status);
		this.event = event;
		this.id = id;
		this.ticketGroupId = ticketGroupId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public TicketNetworkListing toMarketplaceListing() {
		return new TicketNetworkListing(id, this.event.getMarketplaceEventId(), this.getRequest(), ticketGroupId);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "ticketGroupId");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TicketNetworkCachedListing)) {
			return false;
		}
		TicketNetworkCachedListing rhs = (TicketNetworkCachedListing) obj;
		return EqualsBuilder.reflectionEquals(this, rhs, "ticketGroupId");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
