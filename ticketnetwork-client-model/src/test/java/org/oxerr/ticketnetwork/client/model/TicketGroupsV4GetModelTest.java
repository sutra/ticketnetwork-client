package org.oxerr.ticketnetwork.client.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class TicketGroupsV4GetModelTest {

	private static ObjectMapper objectMapper;

	@BeforeAll
	static void setUpBeforeClass() {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.setSerializationInclusion(Include.NON_ABSENT);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
		objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
	}

	@Test
	void testTotalCount0() throws IOException {
		try (InputStream src = this.getClass().getResourceAsStream("TicketGroupsV4GetModel-totalCount-0.json")) {
			TicketGroupsV4GetModel ticketGroupsV4GetModel = objectMapper.readValue(src, TicketGroupsV4GetModel.class);
			assertEquals(0, ticketGroupsV4GetModel.getTotalCount().intValue());
			assertEquals(1, ticketGroupsV4GetModel.getPage().intValue());
			assertEquals(0, ticketGroupsV4GetModel.getCount().intValue());
			assertEquals("https://www.tn-apis.com/inventory/v5/ticketgroups", ticketGroupsV4GetModel.getLinks().get(0).getHref());
			assertEquals("self", ticketGroupsV4GetModel.getLinks().get(0).getRel());
			assertEquals("https://www.tn-apis.com/inventory/v5/ticketgroups?page=1&perPage=50", ticketGroupsV4GetModel.getLinks().get(1).getHref());
			assertEquals("first", ticketGroupsV4GetModel.getLinks().get(1).getRel());
			assertEquals("https://www.tn-apis.com/inventory/v5/ticketgroups?page=0&perPage=50", ticketGroupsV4GetModel.getLinks().get(2).getHref());
			assertEquals("last", ticketGroupsV4GetModel.getLinks().get(2).getRel());
		}
	}

	@Test
	void testTotalCount1() throws IOException {
		try (InputStream src = this.getClass().getResourceAsStream("TicketGroupsV4GetModel-totalCount-1.json")) {
			TicketGroupsV4GetModel ticketGroupsV4GetModel = objectMapper.readValue(src, TicketGroupsV4GetModel.class);

			TicketGroup ticketGroup = ticketGroupsV4GetModel.getResults().get(0);
			assertEquals(11735, ticketGroup.getBrokerId().intValue());
			assertEquals(-258482, ticketGroup.getTicketGroupId().intValue());
			assertEquals(OffsetDateTime.parse("2025-02-28T06:04:06.3620075-05:00"), ticketGroup.getCreated());
			assertEquals(OffsetDateTime.parse("2025-02-28T06:04:06.3620075-05:00"), ticketGroup.getUpdated());

			Event event = ticketGroup.getEvent();
			assertEquals(6953073, event.getId().intValue());
			assertEquals("Noir Silence", event.getName());
			assertEquals(OffsetDateTime.parse("2026-04-18T00:00:00-04:00"), event.getDate());
			assertEquals(LocalTime.parse("20:00:00"), event.getTime());
			assertEquals(OffsetDateTime.parse("2026-04-18T20:00:00-04:00"), event.getDateTime());

			Venue venue = event.getVenue();
			assertEquals(27759, venue.getId().intValue());
			assertEquals("Le Club Square Dix30", venue.getName());

			Address address = venue.getAddress();
			assertEquals("9200 Boulevard Leduc", address.getStreet1());
			assertEquals("", address.getStreet2());
			assertEquals("Brossard", address.getCity());
			assertEquals("QC", address.getState());
			assertEquals("J4Y 0L1", address.getPostalCode());
			assertEquals("CA", address.getCountryCode());

			Category category = event.getCategories().get(0);
			assertEquals(982, category.getId().intValue());
			assertEquals("CONCERTS-->POP / ROCK", category.getDescription());

			assertEquals("https://d22t3g1yodrpj2.cloudfront.net/LeClubSquareDix30_EndstageTables2_2024-09-30_2024-09-30_0900_09302024_092226_SVGC_tn.png", event.getStaticMapUrl());
			assertEquals(73478, event.getVenueConfigurationId().intValue());

			assertNull(ticketGroup.getZone());
			assertNull(ticketGroup.getView());

			Seats seats = ticketGroup.getSeats();
			assertEquals("BALCON TABLE 11", seats.getSection());
			assertEquals("11", seats.getRow());
			assertEquals("Balcony Table 11", seats.getStandardSection());
			assertEquals("Balc$Tbl$11", seats.getCanonicalSection());
			assertEquals("Balc Tbl", seats.getLevelCode());

			SeatingType seatingType = seats.getSeatingType();
			assertEquals(1, seatingType.getId().intValue());
			assertEquals("Odd/Even", seatingType.getDescription());

			assertEquals(1, seats.getLowSeat().intValue());
			assertEquals(1, seats.getHighSeat().intValue());
			assertEquals(1, seats.getAvailableLowSeat().intValue());
			assertEquals(1, seats.getAvailableHighSeat().intValue());

			UnitPriceGetModel unitPriceGetModel = ticketGroup.getUnitPrice();

			MoneyAmountModel wholesalePrice = unitPriceGetModel.getWholesalePrice();
			assertEquals(new BigDecimal("51.7500"), wholesalePrice.getValue());
			assertEquals("USD", wholesalePrice.getCurrencyCode());

			MoneyAmountModel retailPrice = unitPriceGetModel.getRetailPrice();
			assertEquals(new BigDecimal("5175.0000"), retailPrice.getValue());
			assertEquals("USD", retailPrice.getCurrencyCode());

			MoneyAmountModel facePrice = unitPriceGetModel.getFacePrice();
			assertEquals(new BigDecimal("5175.0000"), facePrice.getValue());
			assertEquals("USD", facePrice.getCurrencyCode());

			MoneyAmountModel cost = unitPriceGetModel.getCost();
			assertEquals(new BigDecimal("51.7500"), cost.getValue());
			assertEquals("USD", cost.getCurrencyCode());

			MoneyAmountModel taxedCost = unitPriceGetModel.getTaxedCost();
			assertEquals(new BigDecimal("0.0"), taxedCost.getValue());
			assertEquals("USD", taxedCost.getCurrencyCode());

			MoneyAmountModel msrp = unitPriceGetModel.getMsrp();
			assertEquals(new BigDecimal("0.0"), msrp.getValue());
			assertEquals("USD", msrp.getCurrencyCode());

			assertTrue(ticketGroup.getIsOnHand().booleanValue());
			assertEquals("2025-02-28", ticketGroup.getOnHandDate().toString());
			assertFalse(ticketGroup.getIsInstant().booleanValue());
			assertEquals(1, ticketGroup.getReferenceTicketGroupId().intValue());

			StockType stockType = ticketGroup.getStockType();
			assertEquals(1, stockType.getId().intValue());
			assertEquals("Standard", stockType.getDescription());

			TicketGroupType ticketGroupType = ticketGroup.getTicketGroupType();
			assertEquals(1, ticketGroupType.getId().intValue());
			assertEquals("Event Ticket", ticketGroupType.getDescription());

			SplitRule splitRule = ticketGroup.getSplitRule();
			assertEquals(1, splitRule.getId().intValue());
			assertEquals("Standard Split Rule", splitRule.getDescription());

			Quantity quantity = ticketGroup.getQuantity();
			assertEquals(1, quantity.getTotal().intValue());
			assertEquals(1, quantity.getAvailable().intValue());
			assertEquals(0, quantity.getHeld().intValue());
			assertEquals(0, quantity.getSold().intValue());

			assertTrue(ticketGroup.getTickets().isEmpty());
			assertEquals("", ticketGroup.getBroadcastChannelIds());

			Notes notes = ticketGroup.getNotes();
			assertNull(notes.getExternal());
			assertNull(notes.getInternal());
			assertTrue(notes.getAdditional().isEmpty());

			NearTerm nearTerm = ticketGroup.getNearTerm();

			NearTermDeliveryMethod nearTermDeliveryMethod = nearTerm.getNearTermDeliveryMethod();
			assertEquals(0, nearTermDeliveryMethod.getId().intValue());
			assertEquals("Near-Term Special Delivery", nearTermDeliveryMethod.getDescription());

			NearTermDisplay nearTermDisplay = nearTerm.getNearTermDisplay();
			assertEquals(0, nearTermDisplay.getId().intValue());
			assertEquals("Standard delivery until near-term", nearTermDisplay.getDescription());

			assertNull(ticketGroup.getOrders());
			assertNull(ticketGroup.getLastUpdatedBy());
			assertFalse(ticketGroup.getHideSeats().booleanValue());
			assertFalse(ticketGroup.getPending().booleanValue());
			assertFalse(ticketGroup.getIsMercury().booleanValue());
			assertFalse(ticketGroup.getIsTNPrime().booleanValue());
			assertFalse(ticketGroup.getIsShort().booleanValue());
			assertFalse(ticketGroup.getHasBarcodes().booleanValue());
			assertFalse(ticketGroup.getHasPurchaseOrder().booleanValue());
			assertTrue(ticketGroup.getPurchaseOrderIds().isEmpty());
			assertTrue(ticketGroup.getTags().isEmpty());
			assertFalse(ticketGroup.getHasQrScreenshots().booleanValue());

			Autopricer autopricer = ticketGroup.getAutopricer();
			assertFalse(autopricer.getIsActive().booleanValue());
			assertNull(autopricer.getEndPrice());
			assertNull(autopricer.getPercentOfWholesale());
			assertNull(autopricer.getStartDaysBeforeEvent());
			assertNull(autopricer.getEndDaysBeforeEvent());

			assertTrue(ticketGroup.getThirdPartyExchangeListings().isEmpty());
			assertFalse(ticketGroup.getIsNatbBroker().booleanValue());

			Link link0 = ticketGroup.getLinks().get(0);
			assertEquals("https://www.tn-apis.com/inventory/v5/ticketgroups/-258482", link0.getHref());
			assertEquals("ea:tickets", link0.getRel());

			Link link1 = ticketGroup.getLinks().get(1);
			assertEquals("https://www.tn-apis.com/inventory/v5/ticketgroups/-258482/etickets", link1.getHref());
			assertEquals("etickets", link1.getRel());

			assertEquals(1, ticketGroupsV4GetModel.getTotalCount().intValue());
			assertEquals(1, ticketGroupsV4GetModel.getPage().intValue());
			assertEquals(1, ticketGroupsV4GetModel.getCount().intValue());
			assertEquals("https://www.tn-apis.com/inventory/v5/ticketgroups", ticketGroupsV4GetModel.getLinks().get(0).getHref());
			assertEquals("self", ticketGroupsV4GetModel.getLinks().get(0).getRel());
			assertEquals("https://www.tn-apis.com/inventory/v5/ticketgroups?page=1&perPage=50", ticketGroupsV4GetModel.getLinks().get(1).getHref());
			assertEquals("first", ticketGroupsV4GetModel.getLinks().get(1).getRel());
			assertEquals("https://www.tn-apis.com/inventory/v5/ticketgroups?page=1&perPage=50", ticketGroupsV4GetModel.getLinks().get(2).getHref());
			assertEquals("last", ticketGroupsV4GetModel.getLinks().get(2).getRel());
		}
	}

}
