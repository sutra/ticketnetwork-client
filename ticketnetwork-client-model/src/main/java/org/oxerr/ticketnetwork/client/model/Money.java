package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Money implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The decimal value of the money amount. PATCH operations: add, replace, test.
	 */
	private BigDecimal value;

	/**
	 * The ISO 4217 currency code of the money amount.
	 */
	private String currencyCode;

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

}
