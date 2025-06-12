package org.oxerr.ticketnetwork.client.rescu.resource.inventory;

import java.util.List;

import org.oxerr.ticketnetwork.client.model.AdditionalNoteClassesGetModel;
import org.oxerr.ticketnetwork.client.model.AdditionalNoteDescriptionsGetModel;
import org.oxerr.ticketnetwork.client.model.BroadcastChannelsGetModel;
import org.oxerr.ticketnetwork.client.model.NearTermShippingMethodGetModel;
import org.oxerr.ticketnetwork.client.model.SeatingTypesGetModel;
import org.oxerr.ticketnetwork.client.model.StockTypesGetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupTypesGetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4GetModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupsV4GetModel;
import org.oxerr.ticketnetwork.client.rescu.resource.TicketNetworkException;

import com.github.fge.jsonpatch.JsonPatch;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

/**
 * A RESTful API that lets subscribers interact with tickets for brokers.
 */
@Path("/inventory/v5")
public interface InventoryResource {

	/**
	 * Search for ticket groups by filter.
	 * 
	 * <p>
	 * Multiple parameters are treated as
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
	 * </p>
	 *
	 * <p>
	 * If successful, the HTTP response code will indicate a 200 (OK) response,
	 * and a representation of the ticket groups will be included in the response.
	 * </p>
	 *
	 * <b>Filterable Properties</b>
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
	 * <b>Sortable Properties</b>
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
	 * @throws TicketNetworkException if a business exception occurs.
	 */
	@GET
	@Path("/ticketgroups")
	@Produces(MediaType.APPLICATION_JSON)
	TicketGroupsV4GetModel getTicketGroups(
		@QueryParam("hasEticket") Boolean hasEticket,
		@QueryParam("pending") Boolean pending,
		@QueryParam("returnTicketsData") Boolean returnTicketsData,
		@QueryParam("perPage") Integer perPage,
		@QueryParam("page") Integer page,
		@QueryParam("skip") Integer skip,
		@QueryParam("$filter") String filter,
		@QueryParam("$orderby") String orderby
	) throws TicketNetworkException;

	/**
	 * Create a ticket group.
	 *
	 * <p>
	 * If successful, the HTTP response code will indicate a 201 (Created)
	 * response, and a representation of the ticket group will be included in
	 * the response.
	 * </p>
	 *
	 * @param ticketGroupV4PostModel Object containing details of the ticket
	 * group to create.
	 * @return the created ticket group.
	 * @throws TicketNetworkException if a business exception occurs.
	 */
	@POST
	@Path("/ticketgroups")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	TicketGroupV4GetModel createTicketGroup(TicketGroupV4PostModel ticketGroupV4PostModel)
		throws TicketNetworkException;

	/**
	 * Gets a single ticket group by ID.
	 *
	 * <p>
	 * If successful, the HTTP response code will indicate a 200 (OK) response,
	 * and a representation of the ticket group will be included in the response.
	 * </p>
	 *
	 * @param ticketGroupId The ticket group ID.
	 * @return the ticket group.
	 * @throws TicketNetworkException if a business exception occurs.
	 */
	@GET
	@Path("/ticketgroups/{ticketGroupId}")
	@Produces(MediaType.APPLICATION_JSON)
	TicketGroupV4GetModel getTicketGroup(@PathParam("ticketGroupId") Integer ticketGroupId)
		throws TicketNetworkException;

	/**
	 * Deletes a single ticket group by ID.
	 *
	 * <p>
	 * If successful, the HTTP response code will indicate a 204 (No Content)
	 * response. If there is not currently a QR screenshot attached, the call
	 * will fail.
	 * </p>
	 *
	 * @param ticketGroupId The ticket group ID.
	 * @throws TicketNetworkException if a business exception occurs.
	 */
	@DELETE
	@Path("/ticketgroups/{ticketGroupId}")
	@Produces(MediaType.APPLICATION_JSON)
	void deleteTicketGroup(@PathParam("ticketGroupId") Integer ticketGroupId)
		throws TicketNetworkException;

	/**
	 * Updates an ticket group according to a list of PATCH operations.
	 *
	 * <p>
	 * Include the following media type in the request HTTP Headers:
	 * {@code Content-Type: application/json-patch+json}.
	 * For the full IETF specification on PATCH syntax see
	 * <a href="http://tools.ietf.org/html/rfc6902">JavaScript Object Notation (JSON) Patch – RFC6902</a>.
	 * </p>
	 * 
	 * <p>
	 * If successful, the HTTP response code will indicate a 200 (OK) response,
	 * and a representation of the newly modified order (after the PATCH
	 * operations have been completed) will be included in the response.
	 * On failure, the API will respond with a 400 (Bad Request) with
	 * appropriate validation message(s).
	 * </p>
	 *
	 * <b>Patchable Properties</b>
	 * <p>
	 * <b>Add</b>:
	 * event/id, seats/section, seats/row, seats/standardSection,
	 * seats/canonicalSection, seats/seatingType/id, seats/lowSeat,
	 * unitPrice/wholesalePrice/value, unitPrice/wholesalePrice/currencyCode,
	 * unitPrice/retailPrice/value, unitPrice/retailPrice/currencyCode,
	 * unitPrice/facePrice/currencyCode, unitPrice/facePrice/value,
	 * unitPrice/cost/value, unitPrice/cost/currencyCode,
	 * unitPrice/taxedCost/value, unitPrice/taxedCost/currencyCode,
	 * unitPrice/msrp/value, unitPrice/msrp/currencyCode, isOnHand, onHandDate,
	 * referenceTicketGroupId, stockType/id, ticketGroupType/id, splitRule/id,
	 * broadcastChannelIds, notes/external, notes/internal, notes/additional,
	 * nearTerm/nearTermDeliveryMethod/id, nearTerm/nearTermDisplay/id, pending,
	 * tags, autopricer/endPrice, autopricer/percentOfWholesale,
	 * autopricer/startDaysBeforeEvent, autopricer/endDaysBeforeEvent.
	 * </p>
	 *
	 * <p>
	 * <b>Replace</b>:
	 * event/id, seats/section, seats/row, seats/standardSection,
	 * seats/canonicalSection, seats/seatingType/id, seats/lowSeat,
	 * unitPrice/wholesalePrice/value, unitPrice/wholesalePrice/currencyCode,
	 * unitPrice/retailPrice/value, unitPrice/retailPrice/currencyCode,
	 * unitPrice/facePrice/currencyCode, unitPrice/facePrice/value,
	 * unitPrice/cost/value, unitPrice/cost/currencyCode,
	 * unitPrice/taxedCost/value, unitPrice/taxedCost/currencyCode,
	 * unitPrice/msrp/value, unitPrice/msrp/currencyCode, isOnHand, onHandDate,
	 * referenceTicketGroupId, stockType/id, ticketGroupType/id, splitRule/id,
	 * quantity/total, broadcastChannelIds, notes/external, notes/internal,
	 * notes/additional, nearTerm/nearTermDeliveryMethod/id,
	 * nearTerm/nearTermDisplay/id, hideSeats, pending, isShort, tags,
	 * autopricer/isActive, autopricer/endPrice, autopricer/percentOfWholesale,
	 * autopricer/startDaysBeforeEvent, autopricer/endDaysBeforeEvent.
	 * </p>
	 *
	 * <p>
	 * <b>Remove</b>:
	 * referenceTicketGroupId, broadcastChannelIds, notes/external,
	 * notes/internal, notes/additional, tags.
	 * </p>
	 *
	 * @param ticketGroupId The ticket group ID.
	 * @param patchOperations A list of operations to perform on the ticket
	 * group in the order in which they should be applied.
	 *
	 * @return OK
	 * @throws TicketNetworkException if a business exception occurs.
	 * The model provided in the body is invalid, one or more of the operations
	 * is not supported, or performing one or more of the operations would put
	 * the ticket groups in an invalid state.
	 */
	@PATCH
	@Path("/ticketgroups/{ticketGroupId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	TicketGroupV4GetModel updateTicketGroup(
		@PathParam("ticketGroupId") Integer ticketGroupId,
		JsonPatch patchOperations
	) throws TicketNetworkException;

	/**
	 * Search for ticket groups by filter.
	 *
	 * <p>
	 * Search for ticket groups by filter. Multiple parameters are treated as AND. For ticket-level properties, if any ticket in the ticket group matches, the entire ticket group is included. Refer to the OData standard for more information on [$filter](http://docs.oasis-open.org/odata/odata/v4.0/errata02/os/complete/part2-url-conventions/odata-v4.0-errata02-os-part2-url-conventions-complete.html#_Toc406398094) and [$orderby](http://docs.oasis-open.org/odata/odata/v4.0/errata02/os/complete/part2-url-conventions/odata-v4.0-errata02-os-part2-url-conventions-complete.html#_Toc406398164). The supported operations for $filter are: 'eq', 'ne', 'gt', 'ge', 'lt', 'le', 'and', 'in', '[contains](http://docs.oasis-open.org/odata/odata/v4.0/errata02/os/complete/part2-url-conventions/odata-v4.0-errata02-os-part2-url-conventions-complete.html#_Toc406398117)', '[startswith](http://docs.oasis-open.org/odata/odata/v4.0/errata02/os/complete/part2-url-conventions/odata-v4.0-errata02-os-part2-url-conventions-complete.html#_Toc406398119)'. The syntax for 'in' is in(path,[val1,val2,...]).
	 * </p>
	 * <p>
	 * If successful, the HTTP response code will indicate a 200 (OK) response, and a representation of the ticket groups will be included in the response.
	 * </p>
	 * <b>Filterable Properties</b>
	 * ticketGroupId, exchangeTicketGroupId, created, updated, onHandDate, notes, broadcastChannelId, isInstant, referenceTicketGroupId, isMercury, isTNPrime, isShort, hasBarcodes, hasPurchaseOrder, purchaseOrderIds, hasQrScreenshots, isNatbBroker, pricingBadgeLevel, event/id, event/date, event/time, event/venueConfigurationId, event/venue/id, event/categories/id, event/categories/description, seats/section, seats/row, seats/standardSection, seats/canonicalSection, unitPrice/wholesalePrice/value, unitPrice/retailPrice/value, unitPrice/facePrice/value, unitPrice/cost/value, unitPrice/taxedCost/value, unitPrice/msrp/value, stockType/id, ticketGroupType/description, splitRule/id, splitRule/description, quantity/total, quantity/available, quantity/purchasable, tickets/seat, tickets/status, tickets/ticketRequestId, tickets/mercuryTransactionId, nearTerm/nearTermDeliveryMethod/id, nearTerm/nearTermDisplay/id, lastUpdatedBy/userId, tags/tag, thirdPartyExchangeListings/thirdPartyExchangeId, thirdPartyExchangeListings/thirdPartyExchangeListingId, thirdPartyExchangeListings/thirdPartyExchangeListingStatus, thirdPartyExchangeListings/checkedAtDateTime, autopricer/isActive
	 * <b>Sortable Properties</b>
	 * mine, ticketGroupId, created, updated, onHandDate, isInstant, referenceTicketGroupId, isShort, purchaseOrderIds, hasQrScreenshots, isNatbBroker, pricingBadgeLevel, event/id, event/name, event/date, event/time, event/categories/id, event/categories/description, seats/section, seats/row, seats/lowSeat, unitPrice/wholesalePrice/value, unitPrice/retailPrice/value, unitPrice/facePrice/value, unitPrice/cost/value, unitPrice/taxedCost/value, unitPrice/msrp/value, stockType/id, stockType/description, ticketGroupType/description, splitRule/description, quantity/total, quantity/available, thirdPartyExchangeListings/thirdPartyExchangeListingId, thirdPartyExchangeListings/thirdPartyExchangeListingStatus, thirdPartyExchangeListings/checkedAtDateTime
	 *
	 * @param hasEticket When true, only ticket groups with eTickets attached to all tickets will be included, otherwise no restriction. Defaults to false.
	 * @param pending When true, only ticket groups with pending = true will be included, otherwise those ticket groups will be excluded. Defaults to false.
	 * @param includeTicketNetworkInventory if set to true include inventory that is owned by other brokers and listed on the TN exchange.
	 * @param returnTicketsData if set to true include data for individual tickets, otherwise only return ticket group-level data.
	 * @param perPage The number of results per page.
	 * @param page The page number.
	 * @param skip The number of results to skip before returning records. If specified, overrides the page parameter.
	 * @param filter The OData filter string. This parameter cannot currently be used with Swagger's "Try it out!".
	 * @param orderby The OData sorting string. This parameter cannot currently be used with Swagger's "Try it out!".
	 * @return the ticket groups.
	 * @throws TicketNetworkException if a business exception occurs.
	 */
	@GET
	@Path("/ticketgroups/all")
	@Produces(MediaType.APPLICATION_JSON)
	TicketGroupsV4GetModel getAllTicketGroups(
		@QueryParam("hasEticket") Boolean hasEticket,
		@QueryParam("pending") Boolean pending,
		@QueryParam("includeTicketNetworkInventory") Boolean includeTicketNetworkInventory,
		@QueryParam("returnTicketsData") Integer returnTicketsData,
		@QueryParam("perPage") Integer perPage,
		@QueryParam("page") Integer page,
		@QueryParam("skip") Integer skip,
		@QueryParam("$filter") String filter,
		@QueryParam("$orderby") String orderby
	) throws TicketNetworkException;

	/**
	 * Gets a listing of all possible ticket group additional note classes.
	 *
	 * If successful, the HTTP response code will indicate a 200 (OK) response,
	 * and a representation of all the possible ticket group additional note
	 * classes will be included in the response.
	 * Note that this is infrequently modified reference data,
	 * and caching this response is recommended.
	 *
	 * @return Additional note classes retrieved.
	 * @throws TicketNetworkException if a business exception occurs.
	 */
	@GET
	@Path("/ticketgroups/additionalnoteclasses")
	@Produces(MediaType.APPLICATION_JSON)
	AdditionalNoteClassesGetModel getAdditionalNoteClasses()
		throws TicketNetworkException;

	/**
	 * Gets a listing of all possible ticket group additional note descriptions.
	 *
	 * If successful, the HTTP response code will indicate a 200 (OK) response,
	 * and a representation of all the possible ticket group additional note
	 * descriptions will be included in the response.
	 * Note that this is infrequently modified reference data,
	 * and caching this response is recommended.
	 *
	 * @return Additional note descriptions retrieved.
	 * @throws TicketNetworkException if a business exception occurs.
	 */
	@GET
	@Path("/ticketgroups/additionalnotes")
	@Produces(MediaType.APPLICATION_JSON)
	AdditionalNoteDescriptionsGetModel getAdditionalNotes()
		throws TicketNetworkException;

	/**
	 * Gets a listing of all of the broadcast channels.
	 *
	 * If successful, the HTTP response code will indicate a 200 (OK) response,
	 * and a representation of the requested data
	 * will be included in the response.
	 * Note that this is infrequently modified reference data,
	 * and caching this response is recommended.
	 *
	 * @return the broadcast channels.
	 * @throws TicketNetworkException if a business exception occurs.
	 */
	@GET
	@Path("/ticketgroups/broadcastchannels")
	@Produces(MediaType.APPLICATION_JSON)
	BroadcastChannelsGetModel getBroadcastChannels()
		throws TicketNetworkException;

	/**
	 * Gets all of the near-term shipping methods.
	 *
	 * If successful, the HTTP response code will indicate a 200 (OK) response,
	 * and a representation of all the possible near-term shipping methods
	 * will be included in the response.
	 * Note that this is infrequently modified reference data,
	 * and caching this response is recommended.
	 *
	 * @return the near-term shipping methods.
	 * @throws TicketNetworkException if a business exception occurs.
	 */
	@GET
	@Path("/ticketgroups/neartermdeliverymethods")
	@Produces(MediaType.APPLICATION_JSON)
	List<NearTermShippingMethodGetModel> getNearTermShippingMethods()
		throws TicketNetworkException;

	/**
	 * Gets a listing of all possible ticket group seating types.
	 *
	 * If successful, the HTTP response code will indicate a 200 (OK) response,
	 * and a representation of all the possible ticket group seating types
	 * will be included in the response.
	 * Note that this is infrequently modified reference data,
	 * and caching this response is recommended.
	 *
	 * @return the seating types.
	 * @throws TicketNetworkException if a business exception occurs.
	 */
	@GET
	@Path("/ticketgroups/seatingtypes")
	@Produces(MediaType.APPLICATION_JSON)
	SeatingTypesGetModel getSeatingTypes() throws TicketNetworkException;

	/**
	 * Gets a listing of all possible ticket group stock types.
	 *
	 * If successful, the HTTP response code will indicate a 200 (OK) response,
	 * and a representation of all the possible ticket group stock types
	 * will be included in the response.
	 * Note that this is infrequently modified reference data,
	 * and caching this response is recommended.
	 * @return the stock types.
	 * @throws TicketNetworkException if a business exception occurs.
	 */
	@GET
	@Path("/ticketgroups/stocktypes")
	@Produces(MediaType.APPLICATION_JSON)
	StockTypesGetModel getStockTypes() throws TicketNetworkException;

	/**
	 * Gets a listing of all possible ticket group types.
	 *
	 * If successful, the HTTP response code will indicate a 200 (OK) response,
	 * and a representation of all the possible ticket group types
	 * will be included in the response.
	 * Note that this is infrequently modified reference data,
	 * and caching this response is recommended.
	 * @return the ticket group types.
	 * @throws TicketNetworkException if a business exception occurs.
	 */
	@GET
	@Path("/ticketgroups/types")
	@Produces(MediaType.APPLICATION_JSON)
	TicketGroupTypesGetModel getTypes() throws TicketNetworkException;

}
