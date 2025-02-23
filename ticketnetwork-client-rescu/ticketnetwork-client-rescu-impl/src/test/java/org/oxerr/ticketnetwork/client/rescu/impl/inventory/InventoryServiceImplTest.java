package org.oxerr.ticketnetwork.client.rescu.impl.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.oxerr.ticketnetwork.client.inventory.InventoryService;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4GetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupsV4GetModel;
import org.oxerr.ticketnetwork.client.rescu.impl.ResCUTicketNetworkClients;
import org.oxerr.ticketnetwork.client.rescu.resource.TicketNetworkException;

class InventoryServiceImplTest {

	private final Logger log = LogManager.getLogger();

	private static InventoryService inventoryService;

	@BeforeAll
	static void setUpBeforeClass() {
		inventoryService = ResCUTicketNetworkClients.getClient().getInventoryService();
	}

	@Test
	void testGetTicketGroups() throws IOException {
		Boolean hasEticket = null;
		Boolean pending = null;
		Boolean returnTicketsData = null;
		Integer perPage = null;
		Integer page = null;
		Integer skip = null;
		String filter = null;
		String orderby = null;

		TicketGroupsV4GetModel ticketGroups = inventoryService.getTicketGroups(
			hasEticket, pending, returnTicketsData, perPage, page, skip,
			filter, orderby
		);
		log.info("ticket groups: {}", ticketGroups);
		assertEquals(0, ticketGroups.getCount().intValue());
	}

	@Test
	void testCreateTicketGroup() throws IOException {
		TicketGroupV4PostModel ticketGroup = new TicketGroupV4PostModel();
		try {
			TicketGroupV4GetModel created = inventoryService.createTicketGroup(ticketGroup);
			log.info("created ticket group: {}", created);
		} catch (TicketNetworkException e) {
			log.error("Failed to create ticket group.", e);
			e.getValidationErrors().forEach((k, v) -> log.error("{}: {}", k, v));
		}
	}

	@Test
	void testDeleteTicketGroup() throws IOException {
		Integer ticketGroupId = 0;
		inventoryService.deleteTicketGroup(ticketGroupId);
	}

}
