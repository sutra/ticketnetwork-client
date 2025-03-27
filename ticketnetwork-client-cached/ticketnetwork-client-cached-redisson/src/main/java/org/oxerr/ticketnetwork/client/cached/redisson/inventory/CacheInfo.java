package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

class CacheInfo {

	private final String cacheName;

	/**
	 * @see TicketNetworkCachedListing#getId()
	 */
	private final String listingId;

	@Nullable
	private final Integer ticketGroupId;

	public CacheInfo(String cacheName, TicketNetworkCachedListing c) {
		this.cacheName = cacheName;
		this.listingId = c.getId();
		this.ticketGroupId = c.getTicketGroupId();
	}

	public String getCacheName() {
		return cacheName;
	}

	public String getListingId() {
		return listingId;
	}

	public Integer getTicketGroupId() {
		return ticketGroupId;
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
		if (!(obj instanceof CacheInfo)) {
			return false;
		}
		CacheInfo rhs = (CacheInfo) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
