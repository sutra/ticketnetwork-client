package org.oxerr.ticketnetwork.client.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

class TicketGroupsV4GetModelTest {

	private static ObjectMapper objectMapper;

	@BeforeAll
	static void setUpBeforeClass() {
		objectMapper = new ObjectMapper();
	}

	@Test
	void test() throws IOException {
		try (InputStream src = this.getClass().getResourceAsStream("TicketGroupsV4GetModel.json")) {
			TicketGroupsV4GetModel ticketGroupsV4GetModel = objectMapper.readValue(src, TicketGroupsV4GetModel.class);
			assertEquals(0, ticketGroupsV4GetModel.getTotalCount().intValue());
			assertEquals(1, ticketGroupsV4GetModel.getPage().intValue());
			assertEquals(0, ticketGroupsV4GetModel.getCount().intValue());
			assertEquals("https://www.tn-apis.com/inventory/v5/ticketgroups", ticketGroupsV4GetModel.getLinks().get(0).getHref());
			assertEquals("self", ticketGroupsV4GetModel.getLinks().get(0).getRel());
			assertEquals("https://www.tn-apis.com/inventory/v5/ticketgroups?page=1&perPage=50", ticketGroupsV4GetModel.getLinks().get(1).getHref());
			assertEquals("first", ticketGroupsV4GetModel.getLinks().get(1).getRel());
			assertEquals("https://www.tn-apis.com/inventory/v5/ticketgroups?page=0&perPage=50", ticketGroupsV4GetModel.getLinks().get(2).getHref());
			assertEquals("last", ticketGroupsV4GetModel.getLinks().get(2).getRel());
		}
	}

}
