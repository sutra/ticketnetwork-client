package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TicketGroupV4GetModel extends TicketGroup implements Serializable {

	private static final long serialVersionUID = 2025022301L;

	public TicketGroupV4GetModel() {
	}

	public TicketGroupV4GetModel(TicketGroupV4PostModel ticketGroupV4PostModel) {
		super(ticketGroupV4PostModel);
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
		if (!(obj instanceof TicketGroupV4GetModel)) {
			return false;
		}
		TicketGroupV4GetModel rhs = (TicketGroupV4GetModel) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
