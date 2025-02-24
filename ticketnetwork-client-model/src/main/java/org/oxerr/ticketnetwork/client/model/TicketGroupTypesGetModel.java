package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.util.List;

/**
 * Meta-entity for GET ticket group types.
 */
public class TicketGroupTypesGetModel implements Serializable {

	private static final long serialVersionUID = 2025022501L;

	/**
	 * The collection of TicketGroupType objects.
	 */
	private List<TicketGroupType> ticketGroupTypes;

	public List<TicketGroupType> getTicketGroupTypes() {
		return ticketGroupTypes;
	}

	public void setTicketGroupTypes(List<TicketGroupType> ticketGroupTypes) {
		this.ticketGroupTypes = ticketGroupTypes;
	}

}
