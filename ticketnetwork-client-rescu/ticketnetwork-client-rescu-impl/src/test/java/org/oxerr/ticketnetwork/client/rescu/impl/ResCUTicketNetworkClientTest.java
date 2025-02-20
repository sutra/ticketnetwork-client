package org.oxerr.ticketnetwork.client.rescu.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

// @Disabled("Token is required")
class ResCUTicketNetworkClientTest {

	public static ResCUTicketNetworkClient getClient() {
		return ResCUTicketNetworkClients.getClient();
	}

	@Test
	void testGetClient() {
		assertNotNull(ResCUTicketNetworkClientTest.getClient());
	}

	@Test
	void testGetEvents() {
		ResCUTicketNetworkClient client = getClient();
		client.getInventoryService().getTicketGroups();
	}

}
