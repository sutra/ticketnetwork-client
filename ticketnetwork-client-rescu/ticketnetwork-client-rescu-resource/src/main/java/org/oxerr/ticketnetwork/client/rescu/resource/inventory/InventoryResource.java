package org.oxerr.ticketnetwork.client.rescu.resource.inventory;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/inventory/v5")
public interface InventoryResource {

	@GET
	@Path("/ticketgroups")
	void getTicketGroups();

}
