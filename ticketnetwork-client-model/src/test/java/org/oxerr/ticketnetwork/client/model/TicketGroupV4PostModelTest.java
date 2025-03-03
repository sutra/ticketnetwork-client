package org.oxerr.ticketnetwork.client.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

class TicketGroupV4PostModelTest {

	private static ObjectMapper objectMapper;

	@BeforeAll
	static void setUpBeforeClass() {
		objectMapper = ObjectMappers.createObjectMapper();
	}

	@Test
	void test() throws IOException {
		try (InputStream src = getClass().getResourceAsStream("TicketGroupV4PostModel.json")) {
			TicketGroupV4PostModel model = objectMapper.readValue(src, TicketGroupV4PostModel.class);
			assertEquals(6953073, model.getEventId().intValue());
			assertEquals(1, model.getQuantity().intValue());
			assertEquals(1, model.getSeatingTypeId().intValue());
			assertEquals("BALCON TABLE 11", model.getSection());
			assertEquals("11", model.getRow());
			assertEquals("1", model.getLowSeat());

			UnitPricePostModel unitPrice = model.getUnitPrice();
			assertEquals(new BigDecimal("51.75"), unitPrice.getWholesalePrice());
			assertEquals(new BigDecimal("5175"), unitPrice.getRetailPrice());
			assertEquals(new BigDecimal("5175"), unitPrice.getFacePrice().getValue());
			assertEquals("USD", unitPrice.getFacePrice().getCurrencyCode());
			assertEquals(new BigDecimal("51.75"), unitPrice.getCost());

			assertEquals(1, model.getReferenceTicketGroupId().intValue());
		}
	}

}
