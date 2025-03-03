package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Model for unit price for POST requests.
 */
public class UnitPriceGetModel implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The wholesale price per ticket,
	 * in the broker's default currency.
	 * Cannot be negative.
	 */
	private MoneyAmountModel wholesalePrice;

	/**
	 * The retail price per ticket,
	 * in the broker's default currency.
	 * Cannot be negative.
	 */
	private MoneyAmountModel retailPrice;

	/**
	 * The face price per ticket. When faceCurrencyCode is specified,
	 * this value is in the specified currency, otherwise it will use the
	 * broker's default currency. Cannot be negative.
	 */
	private MoneyAmountModel facePrice;

	/**
	 * The cost per ticket (amount the broker paid to purchase each ticket),
	 * in the broker's default currency.
	 * Cannot be negative.
	 */
	private MoneyAmountModel cost;

	/**
	 * The amount of tax that has already been paid on the tickets.
	 */
	private MoneyAmountModel taxedCost;

	/**
	 * The All-In Venue Price, the price you would pay if you were to buy the
	 * tickets directly from the box office right now.
	 */
	private MoneyAmountModel msrp;

	public MoneyAmountModel getWholesalePrice() {
		return wholesalePrice;
	}

	public void setWholesalePrice(MoneyAmountModel wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}

	public MoneyAmountModel getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(MoneyAmountModel retailPrice) {
		this.retailPrice = retailPrice;
	}

	public MoneyAmountModel getFacePrice() {
		return facePrice;
	}

	public void setFacePrice(MoneyAmountModel facePrice) {
		this.facePrice = facePrice;
	}

	public MoneyAmountModel getCost() {
		return cost;
	}

	public void setCost(MoneyAmountModel cost) {
		this.cost = cost;
	}

	public MoneyAmountModel getTaxedCost() {
		return taxedCost;
	}

	public void setTaxedCost(MoneyAmountModel taxedCost) {
		this.taxedCost = taxedCost;
	}

	public MoneyAmountModel getMsrp() {
		return msrp;
	}

	public void setMsrp(MoneyAmountModel msrp) {
		this.msrp = msrp;
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
		if (!(obj instanceof UnitPriceGetModel)) {
			return false;
		}
		UnitPriceGetModel rhs = (UnitPriceGetModel) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
