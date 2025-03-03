package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class NearTerm implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The near term shipping method or set of near term shipping methods that
	 * should be available to customers.
	 */
	private NearTermDeliveryMethod nearTermDeliveryMethod;

	/**
	 * Indicates when the near term shipping method(s) should become available
	 * to customers.
	 */
	private NearTermDisplay nearTermDisplay;

	public NearTermDeliveryMethod getNearTermDeliveryMethod() {
		return nearTermDeliveryMethod;
	}

	public void setNearTermDeliveryMethod(NearTermDeliveryMethod nearTermDeliveryMethod) {
		this.nearTermDeliveryMethod = nearTermDeliveryMethod;
	}

	public NearTermDisplay getNearTermDisplay() {
		return nearTermDisplay;
	}

	public void setNearTermDisplay(NearTermDisplay nearTermDisplay) {
		this.nearTermDisplay = nearTermDisplay;
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
		if (!(obj instanceof NearTerm)) {
			return false;
		}
		NearTerm rhs = (NearTerm) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
