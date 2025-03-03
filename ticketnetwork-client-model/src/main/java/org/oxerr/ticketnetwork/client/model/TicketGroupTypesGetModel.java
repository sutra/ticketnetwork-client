package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Meta-entity for GET ticket group types.
 */
public class TicketGroupTypesGetModel implements Serializable {

	private static final long serialVersionUID = 2025022501L;

	/**
	 * The collection of TicketGroupType objects.
	 */
	private List<TicketGroupType> ticketGroupTypes;

	public List<TicketGroupType> getTicketGroupTypes() {
		return ticketGroupTypes;
	}

	public void setTicketGroupTypes(List<TicketGroupType> ticketGroupTypes) {
		this.ticketGroupTypes = ticketGroupTypes;
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
		if (!(obj instanceof TicketGroupTypesGetModel)) {
			return false;
		}
		TicketGroupTypesGetModel rhs = (TicketGroupTypesGetModel) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
