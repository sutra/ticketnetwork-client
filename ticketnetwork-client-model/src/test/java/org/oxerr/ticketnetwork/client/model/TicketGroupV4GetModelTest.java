package org.oxerr.ticketnetwork.client.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

class TicketGroupV4GetModelTest {

	private static ObjectMapper objectMapper;

	@BeforeAll
	static void setUpBeforeClass() {
		objectMapper = ObjectMappers.createObjectMapper();
	}

	@Test
	void testCreateTicketGroupResponse() throws IOException {
		try (var src = getClass().getResourceAsStream("TicketGroupV4GetModel-createTicketGroup-response.json")) {
			var ticketGroupV4GetModel = objectMapper.readValue(src, TicketGroupV4GetModel.class);
			assertNotNull(ticketGroupV4GetModel);
		}
	}

	@Test
	void testGetTicketGroupResponse() throws IOException {
		try (var src = getClass().getResourceAsStream("TicketGroupV4GetModel-getTicketGroup-response.json")) {
			var ticketGroupV4GetModel = objectMapper.readValue(src, TicketGroupV4GetModel.class);
			assertNotNull(ticketGroupV4GetModel);
		}
	}

}
