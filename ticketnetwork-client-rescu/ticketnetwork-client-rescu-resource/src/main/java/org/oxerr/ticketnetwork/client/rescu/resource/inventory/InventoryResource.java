package org.oxerr.ticketnetwork.client.rescu.resource.inventory;

import org.oxerr.ticketnetwork.client.model.response.inventory.TicketGroupsV4GetModel;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/inventory/v5")
public interface InventoryResource {

	/**
	 * Search for ticket groups by filter. Multiple parameters are treated as
	 * AND. For ticket-level properties, if any ticket in the ticket group
	 * matches, the entire ticket group is included. Refer to the OData standard
	 * for more information on [$filter]
	 * (http://docs.oasis-open.org/odata/odata/v4.0/errata02/os/complete/part2-url-conventions/odata-v4.0-errata02-os-part2-url-conventions-complete.html#_Toc406398094)
	 * and [$orderby]
	 * (http://docs.oasis-open.org/odata/odata/v4.0/errata02/os/complete/part2-url-conventions/odata-v4.0-errata02-os-part2-url-conventions-complete.html#_Toc406398164).
	 * The supported operations for $filter are: 'eq', 'ne', 'gt', 'ge', 'lt',
	 * 'le', 'and', 'in',
	 * '[contains](http://docs.oasis-open.org/odata/odata/v4.0/errata02/os/complete/part2-url-conventions/odata-v4.0-errata02-os-part2-url-conventions-complete.html#_Toc406398117)',
	 * '[startswith](http://docs.oasis-open.org/odata/odata/v4.0/errata02/os/complete/part2-url-conventions/odata-v4.0-errata02-os-part2-url-conventions-complete.html#_Toc406398119)'.
	 * The syntax for 'in' is in(path,[val1,val2,...]).
	 *
	 * <p>
	 * If successful, the HTTP response code will indicate a 200 (OK) response,
	 * and a representation of the ticket groups will be included in the response.
	 * </p>
	 *
	 * <h2>Filterable Properties</h2>
	 *
	 * ticketGroupId, exchangeTicketGroupId, created, updated, onHandDate,
	 * notes, broadcastChannelId, isInstant, referenceTicketGroupId, isMercury,
	 * isTNPrime, isShort, hasBarcodes, hasPurchaseOrder, purchaseOrderIds,
	 * hasQrScreenshots, isNatbBroker, event/id, event/date, event/time,
	 * event/venueConfigurationId, event/venue/id, event/categories/id,
	 * event/categories/description, seats/section, seats/row,
	 * seats/standardSection, seats/canonicalSection,
	 * unitPrice/wholesalePrice/value, unitPrice/retailPrice/value,
	 * unitPrice/facePrice/value, unitPrice/cost/value,
	 * unitPrice/taxedCost/value, unitPrice/msrp/value, stockType/id,
	 * ticketGroupType/description, splitRule/id, splitRule/description,
	 * quantity/total, quantity/available, quantity/purchasable, tickets/seat,
	 * tickets/status, tickets/ticketRequestId, tickets/mercuryTransactionId,
	 * nearTerm/nearTermDeliveryMethod/id, nearTerm/nearTermDisplay/id,
	 * lastUpdatedBy/userId, tags/tag,
	 * thirdPartyExchangeListings/thirdPartyExchangeId,
	 * thirdPartyExchangeListings/thirdPartyExchangeListingId,
	 * thirdPartyExchangeListings/thirdPartyExchangeListingStatus,
	 * thirdPartyExchangeListings/checkedAtDateTime, autopricer/isActive
	 *
	 * <h2>Sortable Properties</h2>
	 *
	 * ticketGroupId, created, updated, onHandDate, isInstant,
	 * referenceTicketGroupId, isShort, purchaseOrderIds, hasQrScreenshots,
	 * isNatbBroker, event/id, event/name, event/date, event/time,
	 * event/categories/id, event/categories/description, seats/section,
	 * seats/row, seats/lowSeat, unitPrice/wholesalePrice/value,
	 * unitPrice/retailPrice/value, unitPrice/facePrice/value,
	 * unitPrice/cost/value, unitPrice/taxedCost/value, unitPrice/msrp/value,
	 * stockType/id, stockType/description, ticketGroupType/description,
	 * splitRule/description, quantity/total, quantity/available,
	 * thirdPartyExchangeListings/thirdPartyExchangeListingId,
	 * thirdPartyExchangeListings/thirdPartyExchangeListingStatus,
	 * thirdPartyExchangeListings/checkedAtDateTime
	 *
	 * @param hasEticket When true, only ticket groups with eTickets attached
	 * to all tickets will be included, otherwise no restriction. Defaults to false.
	 * @param pending When true, only ticket groups with pending = true will be
	 * included, otherwise those ticket groups will be excluded. Defaults to false.
	 * @param returnTicketsData if set to true include data for individual
	 * tickets, otherwise only return ticket group-level data.
	 * @param perPage The number of results per page.
	 * @param page The page number.
	 * @param skip The number of results to skip before returning records.
	 * If specified, overrides the page parameter.
	 * @param filter The OData filter string. This parameter cannot currently
	 * be used with Swagger's "Try it out!".
	 * @param orderby The OData sorting string. This parameter cannot currently
	 * be used with Swagger's "Try it out!".
	 * @return the ticket groups.
	 */
	@GET
	@Path("/ticketgroups")
	TicketGroupsV4GetModel getTicketGroups(
		@QueryParam("hasEticket") Boolean hasEticket,
		@QueryParam("pending") Boolean pending,
		@QueryParam("returnTicketsData") Boolean returnTicketsData,
		@QueryParam("perPage") Integer perPage,
		@QueryParam("page") Integer page,
		@QueryParam("skip") Integer skip,
		@QueryParam("$filter") String filter,
		@QueryParam("$orderby") String orderby
	);

}
