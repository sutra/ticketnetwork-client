package org.oxerr.ticketnetwork.client.rescu.impl.inventory;

import org.oxerr.ticketnetwork.client.inventory.InventoryService;
import org.oxerr.ticketnetwork.client.rescu.resource.InventoryResource;

public class InventoryServiceImpl implements InventoryService {

	private final InventoryResource inventoryResource;

	public InventoryServiceImpl(InventoryResource inventoryResource) {
		this.inventoryResource = inventoryResource;
	}

	public void ticketgroups() {
		this.inventoryResource.ticketgroups();
	}

}
