package org.oxerr.ticketnetwork.client.cxf.resource.inventory;

import java.io.IOException;

import org.oxerr.ticketnetwork.client.model.TicketGroupV4GetModel;

import com.github.fge.jsonpatch.JsonPatch;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * A RESTful API that lets subscribers interact with tickets for brokers.
 */
@Path("/inventory/v5")
public interface InventoryResource {

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
	 * <h3>Patchable Properties</h3>
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
	 * @throws IOException if an I/O error occurs.
	 */
	@PATCH
	@Path("/ticketgroups/{ticketGroupId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	TicketGroupV4GetModel updateTicketGroup(
		@PathParam("ticketGroupId") Integer ticketGroupId,
		JsonPatch patchOperations
	) throws IOException;

}
