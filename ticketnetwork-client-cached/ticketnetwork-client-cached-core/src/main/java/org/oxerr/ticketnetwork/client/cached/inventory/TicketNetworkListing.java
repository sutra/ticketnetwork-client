package org.oxerr.ticketnetwork.client.cached.inventory;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.oxerr.ticket.inventory.support.Listing;
import org.oxerr.ticketnetwork.client.model.Event;
import org.oxerr.ticketnetwork.client.model.TicketGroup;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;

public class TicketNetworkListing extends Listing<String, TicketGroupV4PostModel> {

	private static final long serialVersionUID = 2024101701L;

	/**
	 * The ID of the ticket network event.
	 *
	 * @see Event#getId()
	 */
	private Integer marketplaceEventId;

	/**
	 * The reference (external) ticket group ID.
	 *
	 * @see TicketGroupV4PostModel#getReferenceTicketGroupId()
	 */
	private Integer referenceTicketGroupId;

	/**
	 * The local ID of the ticket group.
	 *
	 * @see TicketGroup#getTicketGroupId()
	 */
	private Integer ticketGroupId;

	public TicketNetworkListing() {
	}

	public TicketNetworkListing(String id, Integer marketplaceEventId, TicketGroupV4PostModel request) {
		this(id, marketplaceEventId, request, null);
	}

	public TicketNetworkListing(String id, Integer marketplaceEventId, TicketGroupV4PostModel request, Integer ticketGroupId) {
		super(id, request);
		this.marketplaceEventId = marketplaceEventId;
		this.referenceTicketGroupId = request.getReferenceTicketGroupId();
		this.ticketGroupId = ticketGroupId;
	}

	public Integer getMarketplaceEventId() {
		return marketplaceEventId;
	}

	public void setMarketplaceEventId(Integer marketplaceEventId) {
		this.marketplaceEventId = marketplaceEventId;
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
		return HashCodeBuilder.reflectionHashCode(this, "ticketGroupId");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TicketNetworkListing)) {
			return false;
		}
		TicketNetworkListing rhs = (TicketNetworkListing) obj;
		return EqualsBuilder.reflectionEquals(this, rhs, "ticketGroupId");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
