package org.oxerr.ticketnetwork.client.rescu.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ResCUTicketNetworkClientTest {

	public static ResCUTicketNetworkClient getClient() {
		return ResCUTicketNetworkClients.getClient();
	}

	@Test
	void testGetClient() {
		assertNotNull(ResCUTicketNetworkClientTest.getClient());
	}

}
