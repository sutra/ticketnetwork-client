package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.oxerr.ticketnetwork.client.model.AdditionalNotePostModel;
import org.oxerr.ticketnetwork.client.model.AdditionalNoteVariablePostModel;
import org.oxerr.ticketnetwork.client.model.MoneyAmountModel;
import org.oxerr.ticketnetwork.client.model.Note;
import org.oxerr.ticketnetwork.client.model.Quantity;
import org.oxerr.ticketnetwork.client.model.Seats;
import org.oxerr.ticketnetwork.client.model.TicketGroup;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;
import org.oxerr.ticketnetwork.client.model.UnitPriceGetModel;
import org.oxerr.ticketnetwork.client.model.UnitPricePostModel;
import org.oxerr.ticketnetwork.client.model.Variable;

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

	@Test
	void testIsSameAdditionalNotes() {
		Note note = new Note();
		AdditionalNotePostModel m = new AdditionalNotePostModel();
		assertTrue(Listings.isSameAdditionalNotes(List.of(note), List.of(m)));
		assertFalse(Listings.isSameAdditionalNotes(List.of(note), List.of(m, m)));
	}

	@Test
	void testIsSameAdditionalNote() {
		Note note = new Note();
		AdditionalNotePostModel m = new AdditionalNotePostModel();
		assertTrue(Listings.isSameAdditionalNote(note, m));
	}

	@Test
	void testIsSameVariables() {
		Variable v1 = new Variable("v1", 1);
		Variable v2 = new Variable("v2", 2);
		AdditionalNoteVariablePostModel m1 = new AdditionalNoteVariablePostModel("v1", 1);
		AdditionalNoteVariablePostModel m2 = new AdditionalNoteVariablePostModel("v2", 2);
		assertTrue(Listings.isSameVariables(List.of(v1), List.of(m1)));
		assertTrue(Listings.isSameVariables(List.of(v1,v2), List.of(m2, m1)));
		assertFalse(Listings.isSameVariables(List.of(v1), List.of(m1, m2)));
	}

	@Test
	void testIsSameVariable() {
		Variable v = new Variable();
		AdditionalNoteVariablePostModel m = new AdditionalNoteVariablePostModel();
		assertTrue(Listings.isSameVariable(v, m));

		m.setValue(1);
		assertFalse(Listings.isSameVariable(v, m));
	}

}
