package org.oxerr.ticketnetwork.client.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

class TicketGroupTypesGetModelTest {

	private static ObjectMapper objectMapper;

	@BeforeAll
	static void setUpBeforeClass() {
		objectMapper = ObjectMappers.createObjectMapper();
	}

	@Test
	void test() throws IOException {
		try (InputStream src = this.getClass().getResourceAsStream("TicketGroupTypesGetModel.json")) {
			TicketGroupTypesGetModel ticketGroupTypesGetModel = objectMapper.readValue(src,
					TicketGroupTypesGetModel.class);
			List<TicketGroupType> ticketGroupTypes = ticketGroupTypesGetModel.getTicketGroupTypes();
			assertNotNull(ticketGroupTypes);
			assertEquals(10, ticketGroupTypes.size());
			TicketGroupType ticketGroupType = ticketGroupTypes.get(0);
			assertEquals(1, ticketGroupType.getId().intValue());
			assertEquals("Event Ticket", ticketGroupType.getDescription());
			org.junit.jupiter.api.Assertions.assertFalse(ticketGroupType.getAllowsCustomNotes().booleanValue());
		}
	}

}
