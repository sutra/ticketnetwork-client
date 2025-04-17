package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class to represent a price value and its currency.
 */
public class MoneyAmountModel implements Comparable<MoneyAmountModel>, Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The decimal value of the money amount. PATCH operations: add, replace, test.
	 */
	private BigDecimal value;

	/**
	 * The ISO 4217 currency code of the money amount.
	 */
	private String currencyCode;

	public static MoneyAmountModel of(BigDecimal value, String currencyCode) {
		return new MoneyAmountModel(value, currencyCode);
	}

	public MoneyAmountModel() {
	}

	public MoneyAmountModel(BigDecimal value, String currencyCode) {
		this.value = value;
		this.currencyCode = currencyCode;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public int compareTo(MoneyAmountModel o) {
		Objects.requireNonNull(o);
		int compare = getCurrencyCode().compareTo(o.getCurrencyCode());
		if (compare == 0) {
			compare = this.value.compareTo(o.value);
		}
		return compare;
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
		if (!(obj instanceof MoneyAmountModel)) {
			return false;
		}
		MoneyAmountModel rhs = (MoneyAmountModel) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return String.format("%s %s", getCurrencyCode(), getValue().toPlainString());
	}

}
