package org.oxerr.ticketnetwork.client.rescu.impl.oauth2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.oxerr.ticketnetwork.client.rescu.impl.ResCUTicketNetworkClients;

class TokenServiceImplTest {

	private final Logger log = LogManager.getLogger();

	@Test
	void testGenerateToken() {
		var grantType = "client_credentials";
		var scope = "api_resource_scope_1 api_resource_scope_2";
		var token = ResCUTicketNetworkClients.getClient().getTokenService().generateToken(grantType, scope);
		log.info("Token: {}", token);
		assertEquals(scope, token.getScope());
		assertEquals("Bearer", token.getTokenType());
		assertNotNull(token.getAccessToken());
	}

}
