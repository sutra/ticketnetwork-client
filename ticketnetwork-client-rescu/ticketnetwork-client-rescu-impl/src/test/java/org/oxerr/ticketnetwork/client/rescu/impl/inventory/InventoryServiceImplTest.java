package org.oxerr.ticketnetwork.client.rescu.impl.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.oxerr.ticketnetwork.client.inventory.AllTicketGroupQuery;
import org.oxerr.ticketnetwork.client.inventory.InventoryService;
import org.oxerr.ticketnetwork.client.inventory.TicketGroupQuery;
import org.oxerr.ticketnetwork.client.model.BroadcastChannelsGetModel;
import org.oxerr.ticketnetwork.client.model.MoneyAmountModel;
import org.oxerr.ticketnetwork.client.model.NearTermShippingMethodGetModel;
import org.oxerr.ticketnetwork.client.model.SeatingTypesGetModel;
import org.oxerr.ticketnetwork.client.model.StockTypesGetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupTypesGetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4GetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupsV4GetModel;
import org.oxerr.ticketnetwork.client.model.UnitPricePostModel;
import org.oxerr.ticketnetwork.client.rescu.impl.ResCUTicketNetworkClients;
import org.oxerr.ticketnetwork.client.rescu.resource.TicketNetworkException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
		String filter = "event/id eq 6562663 and seats/section eq '105' and seats/row eq 'W'";
		TicketGroupQuery q = new TicketGroupQuery();
		q.setFilter(filter);
		q.setPerPage(500);
		TicketGroupsV4GetModel ticketGroups = inventoryService.getTicketGroups(q);
		log.info("ticket groups: {}", ticketGroups);
		ticketGroups.getResults().forEach(
			g -> {
				log.info(
					"ticket group: {} {} {}",
					g.getTicketGroupId(),
					g.getReferenceTicketGroupId(),
					g.getUnitPrice().getWholesalePrice()
				);
				try {
					inventoryService.deleteTicketGroup(g.getTicketGroupId());
				} catch (IOException e) {
					log.error("Failed to delete ticket group: {}", g.getTicketGroupId(), e);
				}
			}
		);
		log.info("ticket groups count: {}/{}", ticketGroups.getCount(), ticketGroups.getTotalCount());
		assertEquals(0, ticketGroups.getCount().intValue());
	}

	@Disabled("Call API")
	@Test
	void testDeleteTicketGroupsWithoutWholesalePrice() throws IOException {
		ExecutorService executor = Executors.newFixedThreadPool(10);

		String filter = "unitPrice/wholesalePrice/value eq 0";
		TicketGroupQuery q = new TicketGroupQuery();
		q.setFilter(filter);
		q.setPerPage(500);

		TicketGroupsV4GetModel ticketGroups;
		do {
			ticketGroups = inventoryService.getTicketGroups(q);
			log.info("ticket groups count: {}/{}", ticketGroups.getCount(), ticketGroups.getTotalCount());

			List<Future<?>> futures = ticketGroups.getResults().stream()
				.map(g -> executor.submit(() -> {
					log.info("Deleting ticket group: {}", g.getTicketGroupId());
					try {
						inventoryService.deleteTicketGroup(g.getTicketGroupId());
					} catch (IOException e) {
						log.error("Failed to delete ticket group: {}", g.getTicketGroupId(), e);
					}
				})).collect(Collectors.toList());

			// Wait for all futures to complete
			for (Future<?> future : futures) {
				try {
					future.get();
				} catch (Exception e) {
					log.error("Failed to delete ticket group.", e);
				}
			}
		} while (ticketGroups.getTotalCount() > 0);

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
		ticketGroup.setLowSeat(1);
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
	void testUpdateTicketGroupWithInvalidPath() throws IOException {
		int ticketGroupId = -397363;
		ObjectMapper objectMapper = new ObjectMapper();
		// TODO: fix the patch
		JsonNode jsonNode = objectMapper.readTree(this.getClass().getResourceAsStream("patch.json"));
		JsonPatch patch = JsonPatch.fromJson(jsonNode);
		try {
			inventoryService.updateTicketGroup(ticketGroupId, patch);
		} catch (TicketNetworkException e) {
			log.error("Failed to update ticket group.", e);
			Optional.ofNullable(e.getValidationErrors())
				.ifPresent(ve -> ve.forEach((k, v) -> log.error("{}: {}", k, v)));
		}
	}

	// {"code":"900910","message":"The access token does not allow you to access the requested resource","description":"User is NOT authorized to access the Resource: /ticketgroups/all. Scope validation failed."}
	@Disabled("Call API")
	@Test
	void testGetAllTicketGroups() throws IOException {
		String filter = "event/id eq 6562663 and seats/section eq '105' and seats/row eq 'W'";
		AllTicketGroupQuery q = new AllTicketGroupQuery();
		q.setFilter(filter);
		TicketGroupsV4GetModel ticketGroups = inventoryService.getAllTicketGroups(q);
		log.info("ticket groups: {}", ticketGroups);
		ticketGroups.getResults().forEach(tg -> log.info("ticket group: {} {}", tg.getTicketGroupId(), tg.getReferenceTicketGroupId()));
		log.info("ticket groups total count: {}", ticketGroups.getTotalCount());
		assertEquals(0, ticketGroups.getCount().intValue());
	}

	@Disabled("Call API")
	@Test
	void testGetBroadcastChannels() throws IOException {
		BroadcastChannelsGetModel broadcastChannelsGetModel = inventoryService.getBroadcastChannels();
		log.info("broadcast channels: {}", broadcastChannelsGetModel);
	}

	@Disabled("Call API")
	@Test
	void testGet() throws IOException {
		List<NearTermShippingMethodGetModel> nearTermShippingMethodGetModel = inventoryService.getNearTermShippingMethods();
		log.info("near term shipping methods: {}", nearTermShippingMethodGetModel);
	}

	@Disabled("Call API")
	@Test
	void testGetSeatingTypes() throws IOException {
		SeatingTypesGetModel seatingTypes = inventoryService.getSeatingTypes();
		log.info("seating types: {}", seatingTypes);
	}

	@Disabled("Call API")
	@Test
	void testGetStockTypes() throws IOException {
		StockTypesGetModel stockTypes = inventoryService.getStockTypes();
		log.info("stock types: {}", stockTypes);
	}
	@Disabled("Call API")
	@Test
	void testGetTypes() throws IOException {
		TicketGroupTypesGetModel types = inventoryService.getTypes();
		log.info("ticket group types: {}", types);
	}

}
