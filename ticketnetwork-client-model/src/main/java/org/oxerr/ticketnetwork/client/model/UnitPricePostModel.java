package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Model for unit price for POST requests.
 */
public class UnitPricePostModel implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The wholesale price per ticket,
	 * in the broker's default currency.
	 * Cannot be negative.
	 */
	private BigDecimal wholesalePrice;

	/**
	 * The retail price per ticket,
	 * in the broker's default currency.
	 * Cannot be negative.
	 */
	private BigDecimal retailPrice;

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
	private BigDecimal cost;

	/**
	 * The amount of tax that has already been paid on the tickets.
	 */
	private BigDecimal taxedCost;

	/**
	 * The All-In Venue Price, the price you would pay if you were to buy the
	 * tickets directly from the box office right now.
	 */
	private BigDecimal msrp;

	public UnitPricePostModel() {
	}

	public UnitPricePostModel(UnitPriceGetModel unitPriceGetModel) {
		this.wholesalePrice = unitPriceGetModel.getWholesalePrice().getValue();
		this.retailPrice = unitPriceGetModel.getRetailPrice().getValue();
		this.facePrice = unitPriceGetModel.getFacePrice();
		this.cost = unitPriceGetModel.getCost().getValue();
		this.taxedCost = unitPriceGetModel.getTaxedCost().getValue();
		this.msrp = unitPriceGetModel.getMsrp().getValue();
	}

	public BigDecimal getWholesalePrice() {
		return wholesalePrice;
	}

	public void setWholesalePrice(BigDecimal wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	public MoneyAmountModel getFacePrice() {
		return facePrice;
	}

	public void setFacePrice(MoneyAmountModel facePrice) {
		this.facePrice = facePrice;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getTaxedCost() {
		return taxedCost;
	}

	public void setTaxedCost(BigDecimal taxedCost) {
		this.taxedCost = taxedCost;
	}

	public BigDecimal getMsrp() {
		return msrp;
	}

	public void setMsrp(BigDecimal msrp) {
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
		if (!(obj instanceof UnitPricePostModel)) {
			return false;
		}
		UnitPricePostModel rhs = (UnitPricePostModel) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
