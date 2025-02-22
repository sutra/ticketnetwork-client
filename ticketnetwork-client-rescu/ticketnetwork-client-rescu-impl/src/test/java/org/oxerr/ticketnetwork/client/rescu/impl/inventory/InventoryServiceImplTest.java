package org.oxerr.ticketnetwork.client.rescu.impl.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.oxerr.ticketnetwork.client.model.response.inventory.TicketGroupsV4GetModel;
import org.oxerr.ticketnetwork.client.rescu.impl.ResCUTicketNetworkClients;

class InventoryServiceImplTest {

	private final Logger log = LogManager.getLogger();

	@Test
	void testGetTicketGroups() {
		Boolean hasEticket = null;
		Boolean pending = null;
		Boolean returnTicketsData = null;
		Integer perPage = null;
		Integer page = null;
		Integer skip = null;
		String filter = null;
		String orderby = null;

		TicketGroupsV4GetModel ticketGroups = ResCUTicketNetworkClients.getClient().getInventoryService().getTicketGroups(
			hasEticket, pending, returnTicketsData, perPage, page, skip,
			filter, orderby
		);
		log.info("{}", ticketGroups);
		assertEquals(0, ticketGroups.getCount().intValue());
	}

}
