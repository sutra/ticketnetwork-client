package org.oxerr.ticketnetwork.client.rescu.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("This test requires the access_token.")
class ResCUTicketNetworkClientTest {

	public static ResCUTicketNetworkClient getClient() {
		return ResCUTicketNetworkClients.getClient();
	}

	@Test
	void testGetClient() {
		assertNotNull(ResCUTicketNetworkClientTest.getClient());
	}

}
