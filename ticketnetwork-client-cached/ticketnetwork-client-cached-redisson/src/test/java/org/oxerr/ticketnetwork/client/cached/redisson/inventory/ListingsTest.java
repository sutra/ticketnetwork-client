package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.oxerr.ticketnetwork.client.model.MoneyAmountModel;
import org.oxerr.ticketnetwork.client.model.Quantity;
import org.oxerr.ticketnetwork.client.model.Seats;
import org.oxerr.ticketnetwork.client.model.TicketGroup;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;
import org.oxerr.ticketnetwork.client.model.UnitPriceGetModel;
import org.oxerr.ticketnetwork.client.model.UnitPricePostModel;

class ListingsTest {

	@Test
	void testIsSame() {
		TicketGroup a = new TicketGroup();
		a.setTicketGroupId(-342786);
		a.setReferenceTicketGroupId(1061163092);
		Seats seats = new Seats();
		seats.setSection("205");
		seats.setRow("T");
		seats.setLowSeat(6);
		seats.setHighSeat(14);
		a.setSeats(seats);
		Quantity quantity = new Quantity();
		quantity.setAvailable(9);
		quantity.setHeld(0);
		quantity.setSold(0);
		quantity.setTotal(9);
		a.setQuantity(quantity);
		UnitPriceGetModel unitPriceGetModel = new UnitPriceGetModel();

		// Wholesale price
		unitPriceGetModel.setWholesalePrice(MoneyAmountModel.of(new BigDecimal("94.6300"), "USD"));

		// Retail price
		unitPriceGetModel.setRetailPrice(MoneyAmountModel.of(new BigDecimal("94.6400"), "USD"));

		// Face price
		unitPriceGetModel.setFacePrice(MoneyAmountModel.of(new BigDecimal("94.9900"), "USD"));
		a.setUnitPrice(unitPriceGetModel);

		TicketGroupV4PostModel b = new TicketGroupV4PostModel();
		b.setReferenceTicketGroupId(1061163092);
		b.setSection("205");
		b.setRow("T");
		b.setLowSeat(6);
		b.setQuantity(9);
		UnitPricePostModel unitPricePostModel = new UnitPricePostModel();

		// Wholesale price
		unitPricePostModel.setWholesalePrice(new BigDecimal("94.63"));

		// Retail price
		unitPricePostModel.setRetailPrice(new BigDecimal("94.64"));

		// Face price
		unitPricePostModel.setFacePrice(MoneyAmountModel.of(new BigDecimal("94.99"), "USD"));

		b.setUnitPrice(unitPricePostModel);

		assertTrue(Listings.isSame(a, b));
	}

}
