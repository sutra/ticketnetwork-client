package org.oxerr.ticketnetwork.client.rescu.impl.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.oxerr.ticketnetwork.client.rescu.impl.ResCUTicketNetworkClients;

class InventoryServiceImplTest {

	private final Logger log = LogManager.getLogger();

	@Test
	void testGetAccessToken() {
		var grantType = "client_credentials";
		var scope = "sections_get standard_section_scope exchange_inventory_read batch_pricing_write";
		var token = ResCUTicketNetworkClients.getClient().getTokenService().generateToken(grantType, scope);
		log.info("Token: {}", token);
		assertEquals("Bearer", token.getTokenType());
		assertNotNull(token.getAccessToken());

	}
	@Test
	void testGetTicketGroups() {
		ResCUTicketNetworkClients.getClient().getInventoryService().getTicketGroups();
	}

}
