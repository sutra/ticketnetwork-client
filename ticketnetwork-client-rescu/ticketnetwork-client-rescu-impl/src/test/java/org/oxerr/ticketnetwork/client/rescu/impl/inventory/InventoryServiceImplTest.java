package org.oxerr.ticketnetwork.client.rescu.impl.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.oxerr.ticketnetwork.client.inventory.InventoryService;
import org.oxerr.ticketnetwork.client.model.MoneyAmountModel;
import org.oxerr.ticketnetwork.client.model.SeatingTypesGetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupTypesGetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4GetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupsV4GetModel;
import org.oxerr.ticketnetwork.client.model.UnitPrice;
import org.oxerr.ticketnetwork.client.rescu.impl.ResCUTicketNetworkClients;
import org.oxerr.ticketnetwork.client.rescu.resource.TicketNetworkException;

@Disabled("This test requires the access_token.")
class InventoryServiceImplTest {

	private final Logger log = LogManager.getLogger();

	private static InventoryService inventoryService;

	@BeforeAll
	static void setUpBeforeClass() {
		inventoryService = ResCUTicketNetworkClients.getClient().getInventoryService();
	}

	@Test
	void testGetTicketGroups() throws IOException {
		Boolean hasEticket = null;
		Boolean pending = null;
		Boolean returnTicketsData = null;
		Integer perPage = null;
		Integer page = null;
		Integer skip = null;
		String filter = null;
		String orderby = null;

		TicketGroupsV4GetModel ticketGroups = inventoryService.getTicketGroups(
			hasEticket, pending, returnTicketsData, perPage, page, skip,
			filter, orderby
		);
		log.info("ticket groups: {}", ticketGroups);
		assertEquals(0, ticketGroups.getCount().intValue());
	}

	@Test
	void testCreateTicketGroup() throws IOException {
		log.info("Creating ticket group...");
		TicketGroupV4PostModel ticketGroup = new TicketGroupV4PostModel();
		ticketGroup.setEventId(6953073);
		ticketGroup.setQuantity(1);
		ticketGroup.setSeatingTypeId(1); // Odd/Even
		UnitPrice unitPrice = new UnitPrice();
		unitPrice.setWholesalePrice(new BigDecimal("51.75"));
		unitPrice.setRetailPrice(new BigDecimal("5175"));
		MoneyAmountModel faceValue = MoneyAmountModel.of(new BigDecimal("5175"), "USD");
		unitPrice.setFacePrice(faceValue);
		unitPrice.setCost(new BigDecimal("51.75"));
		ticketGroup.setUnitPrice(unitPrice);
		ticketGroup.setSection("BALCON TABLE 11");
		ticketGroup.setRow("11");
		ticketGroup.setLowSeat("1");
		ticketGroup.setReferenceTicketGroupId(1884284108541005882L);

		try {
			TicketGroupV4GetModel created = inventoryService.createTicketGroup(ticketGroup);
			log.info("created ticket group: {}", created);
		} catch (TicketNetworkException e) {
			log.error("Failed to create ticket group.", e);
			e.getValidationErrors().forEach((k, v) -> log.error("{}: {}", k, v));
		}
	}

	@Test
	void testDeleteTicketGroup() throws IOException {
		Integer ticketGroupId = 0;
		inventoryService.deleteTicketGroup(ticketGroupId);
	}

	@Test
	void testGetSeatingTypes() throws IOException {
		SeatingTypesGetModel seatingTypes = inventoryService.getSeatingTypes();
		log.info("seating types: {}", seatingTypes);
	}

	@Test
	void testGetTypes() throws IOException {
		TicketGroupTypesGetModel types = inventoryService.getTypes();
		log.info("ticket group types: {}", types);
	}

}
