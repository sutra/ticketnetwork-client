package org.oxerr.ticketnetwork.client;

import org.oxerr.ticketnetwork.client.inventory.InventoryService;
import org.oxerr.ticketnetwork.client.oauth2.TokenService;

public interface TicketNetworkClient {

	TokenService getTokenService();

	InventoryService getInventoryService();

}
