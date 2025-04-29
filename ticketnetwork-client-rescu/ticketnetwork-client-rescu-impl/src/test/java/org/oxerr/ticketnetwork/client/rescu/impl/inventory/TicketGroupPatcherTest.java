package org.oxerr.ticketnetwork.client.rescu.impl.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.oxerr.ticketnetwork.client.model.MoneyAmountModel;
import org.oxerr.ticketnetwork.client.model.TicketGroup;
import org.oxerr.ticketnetwork.client.model.UnitPriceGetModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;

class TicketGroupPatcherTest {

	private final Logger log = LogManager.getLogger();

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final TicketGroupPatcher ticketGroupPatcher = new TicketGroupPatcher(objectMapper);

	@Test
	void testCreatePatch() {
		TicketGroup target = new TicketGroup();
		TicketGroup source = new TicketGroup();

		UnitPriceGetModel sourceUnitPrice = new UnitPriceGetModel();
		sourceUnitPrice.setWholesalePrice(MoneyAmountModel.of(new BigDecimal("0.00"), "USD"));
		source.setUnitPrice(sourceUnitPrice);

		UnitPriceGetModel targetUnitPrice = new UnitPriceGetModel();
		targetUnitPrice.setWholesalePrice(MoneyAmountModel.of(new BigDecimal("1.00"), "USD"));
		target.setUnitPrice(targetUnitPrice);

		JsonPatch patch = ticketGroupPatcher.createPatch(target, source);
		log.info("patch: {}", patch);

		assertEquals("[op: replace; path: \"/unitPrice/wholesalePrice/value\"; value: 1]", patch.toString());
	}

	// Face price and face currency code cannot have different operation types.
	@Test
	void testCreatePatchWithFacePrice() {
		TicketGroup target = new TicketGroup();
		TicketGroup source = new TicketGroup();

		UnitPriceGetModel sourceUnitPrice = new UnitPriceGetModel();
		sourceUnitPrice.setFacePrice(MoneyAmountModel.of(new BigDecimal("0.00"), "USD"));
		source.setUnitPrice(sourceUnitPrice);

		UnitPriceGetModel targetUnitPrice = new UnitPriceGetModel();
		targetUnitPrice.setFacePrice(MoneyAmountModel.of(new BigDecimal("1.00"), "USD"));
		target.setUnitPrice(targetUnitPrice);

		JsonPatch patch = ticketGroupPatcher.createPatch(target, source);
		log.info("patch: {}", patch);

		assertEquals("[op: replace; path: \"/unitPrice/facePrice/value\"; value: 1, op: replace; path: \"/unitPrice/facePrice/currencyCode\"; value: \"USD\"]", patch.toString());
	}

}
