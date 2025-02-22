package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Autopricer implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * Whether or not the auto-pricer is active for this ticket group. PATCH operations: replace, test.
	 */
	private Boolean isActive;

	/**
	 * The price to increase or decrease the current wholesale price to by the
	 * end of the auto-pricing period. PATCH operations: add, replace, test.
	 */
	private BigDecimal endPrice;

	/**
	 * The percent of initial wholesale value to increase or decrease the
	 * wholesale price to by the end of the auto-pricing period.
	 * PATCH operations: add, replace, test.
	 */
	private BigDecimal percentOfWholesale;

	/**
	 * The number of days before the event that auto-pricing turns on for this
	 * ticket group. PATCH operations: add, replace, test.
	 */
	private Integer startDaysBeforeEvent;

	/**
	 * The number of days before the event that auto-pricing turns off for this
	 * ticket group. PATCH operations: add, replace, test.
	 */
	private Integer endDaysBeforeEvent;

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public BigDecimal getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(BigDecimal endPrice) {
		this.endPrice = endPrice;
	}

	public BigDecimal getPercentOfWholesale() {
		return percentOfWholesale;
	}

	public void setPercentOfWholesale(BigDecimal percentOfWholesale) {
		this.percentOfWholesale = percentOfWholesale;
	}

	public Integer getStartDaysBeforeEvent() {
		return startDaysBeforeEvent;
	}

	public void setStartDaysBeforeEvent(Integer startDaysBeforeEvent) {
		this.startDaysBeforeEvent = startDaysBeforeEvent;
	}

	public Integer getEndDaysBeforeEvent() {
		return endDaysBeforeEvent;
	}

	public void setEndDaysBeforeEvent(Integer endDaysBeforeEvent) {
		this.endDaysBeforeEvent = endDaysBeforeEvent;
	}

}
