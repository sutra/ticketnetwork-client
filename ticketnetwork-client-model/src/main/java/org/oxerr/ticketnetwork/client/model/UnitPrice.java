package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

public class UnitPrice implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The wholesale price per ticket, in the broker's default currency.
	 * Cannot be negative.
	 */
	private Money wholesalePrice;

	/**
	 * The retail price per ticket, in the broker's default currency.
	 * Cannot be negative.
	 */
	private Money retailPrice;

	/**
	 * The face price per ticket. When faceCurrencyCode is specified,
	 * this value is in the specified currency, otherwise it will use the
	 * broker's default currency. Cannot be negative.
	 */
	private Money facePrice;

	/**
	 * The cost per ticket (amount the broker paid to purchase each ticket), in
	 * the broker's default currency. Cannot be negative.
	 */
	private Money cost;

	/**
	 * The amount of tax that has already been paid on the tickets.
	 */
	private Money taxedCost;

	/**
	 * The All-In Venue Price, the price you would pay if you were to buy the
	 * tickets directly from the box office right now.
	 */
	private Money msrp;

	public Money getWholesalePrice() {
		return wholesalePrice;
	}

	public void setWholesalePrice(Money wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}

	public Money getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(Money retailPrice) {
		this.retailPrice = retailPrice;
	}

	public Money getFacePrice() {
		return facePrice;
	}

	public void setFacePrice(Money facePrice) {
		this.facePrice = facePrice;
	}

	public Money getCost() {
		return cost;
	}

	public void setCost(Money cost) {
		this.cost = cost;
	}

	public Money getTaxedCost() {
		return taxedCost;
	}

	public void setTaxedCost(Money taxedCost) {
		this.taxedCost = taxedCost;
	}

	public Money getMsrp() {
		return msrp;
	}

	public void setMsrp(Money msrp) {
		this.msrp = msrp;
	}

}
