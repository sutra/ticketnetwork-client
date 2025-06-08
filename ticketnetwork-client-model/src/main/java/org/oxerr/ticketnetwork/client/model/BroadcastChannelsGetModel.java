package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Meta-entity for GET broadcast channels.
 */
public class BroadcastChannelsGetModel implements Serializable {

	private static final long serialVersionUID = 2025042401L;

	private List<IdAndDescriptionGetModel> broadcastChannels;

	public List<IdAndDescriptionGetModel> getBroadcastChannels() {
		return broadcastChannels;
	}

	public void setBroadcastChannels(List<IdAndDescriptionGetModel> broadcastChannels) {
		this.broadcastChannels = broadcastChannels;
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
		if (!(obj instanceof BroadcastChannelsGetModel)) {
			return false;
		}
		BroadcastChannelsGetModel rhs = (BroadcastChannelsGetModel) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
