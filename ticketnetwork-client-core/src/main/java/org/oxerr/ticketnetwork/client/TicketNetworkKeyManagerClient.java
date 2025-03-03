package org.oxerr.ticketnetwork.client;

import org.oxerr.ticketnetwork.client.oauth2.TokenService;

public interface TicketNetworkKeyManagerClient {

	TokenService getTokenService();

}
