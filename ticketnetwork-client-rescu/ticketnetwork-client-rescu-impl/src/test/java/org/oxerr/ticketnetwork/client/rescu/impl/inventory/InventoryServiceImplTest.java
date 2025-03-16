package org.oxerr.ticketnetwork.client.rescu.impl.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

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
import org.oxerr.ticketnetwork.client.model.UnitPricePostModel;
import org.oxerr.ticketnetwork.client.rescu.impl.ResCUTicketNetworkClients;
import org.oxerr.ticketnetwork.client.rescu.resource.TicketNetworkException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.DecimalNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;

@Disabled("This test requires the access_token.")
class InventoryServiceImplTest {

	private final Logger log = LogManager.getLogger();

	private static InventoryService inventoryService;

	@BeforeAll
	static void setUpBeforeClass() {
		inventoryService = ResCUTicketNetworkClients.createClient().getInventoryService();
	}

	@Disabled("Call API")
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
		ticketGroups.getResults().forEach(tg -> log.info("ticket group: {} {}", tg.getTicketGroupId(), tg.getReferenceTicketGroupId()));
		assertEquals(0, ticketGroups.getCount().intValue());
	}

	@Disabled("Create ticket group")
	@Test
	void testCreateTicketGroup() throws IOException {
		log.info("Creating ticket group...");
		TicketGroupV4PostModel ticketGroup = new TicketGroupV4PostModel();
		ticketGroup.setEventId(6953073);
		ticketGroup.setQuantity(1);
		ticketGroup.setSeatingTypeId(1); // Odd/Even
		UnitPricePostModel unitPrice = new UnitPricePostModel();
		unitPrice.setWholesalePrice(new BigDecimal("51.75"));
		unitPrice.setRetailPrice(new BigDecimal("5175"));
		MoneyAmountModel faceValue = MoneyAmountModel.of(new BigDecimal("5175"), "USD");
		unitPrice.setFacePrice(faceValue);
		unitPrice.setCost(new BigDecimal("51.75"));
		ticketGroup.setUnitPrice(unitPrice);
		ticketGroup.setSection("BALCON TABLE 11");
		ticketGroup.setRow("11");
		ticketGroup.setLowSeat("1");
		ticketGroup.setReferenceTicketGroupId(1);

		try {
			TicketGroupV4GetModel created = inventoryService.createTicketGroup(ticketGroup);
			log.info("created ticket group: {}", created);
		} catch (TicketNetworkException e) {
			log.error("Failed to create ticket group.", e);
			e.getValidationErrors().forEach((k, v) -> log.error("{}: {}", k, v));
		}
	}

	@Disabled("Call API")
	@Test
	void testGetTicketGroup() throws IOException {
		Integer ticketGroupId = -258482;
		TicketGroupV4GetModel ticketGroup = inventoryService.getTicketGroup(ticketGroupId);
		log.info("ticket group: {}", ticketGroup);
	}

	@Disabled("Delete ticket group")
	@Test
	void testDeleteTicketGroup() throws IOException {
		Integer ticketGroupId = 0;
		inventoryService.deleteTicketGroup(ticketGroupId);
	}

	@Disabled("Call API")
	@Test
	void testUpdateTicketGroup() throws IOException {
		int ticketGroupId = -258482;
		JsonPatchOperation[] pathOperations = new JsonPatchOperation[1];
		JsonPointer path = JsonPointer.of("unitPrice", "wholesalePrice", "value");
		JsonNode value = DecimalNode.valueOf(new BigDecimal("51.76"));
		pathOperations[0] = new ReplaceOperation(path, value);
		JsonPatch patch = new JsonPatch(Arrays.asList(pathOperations));
		inventoryService.updateTicketGroup(ticketGroupId, patch);
	}

	@Disabled("Call API")
	@Test
	void testGetSeatingTypes() throws IOException {
		SeatingTypesGetModel seatingTypes = inventoryService.getSeatingTypes();
		log.info("seating types: {}", seatingTypes);
	}

	@Disabled("Call API")
	@Test
	void testGetTypes() throws IOException {
		TicketGroupTypesGetModel types = inventoryService.getTypes();
		log.info("ticket group types: {}", types);
	}

}
