package org.oxerr.ticketnetwork.client.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class MoneyAmountModelTest {

	@Test
	void testCompareTo() {
		MoneyAmountModel m1 = new MoneyAmountModel();
		m1.setCurrencyCode("USD");
		m1.setValue(new BigDecimal("1.0"));

		MoneyAmountModel m2 = new MoneyAmountModel();
		m2.setCurrencyCode("USD");
		m2.setValue(new BigDecimal("2.0"));

		assertEquals(-1, m1.compareTo(m2));
	}

}
