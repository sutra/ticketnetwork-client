package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

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

}
