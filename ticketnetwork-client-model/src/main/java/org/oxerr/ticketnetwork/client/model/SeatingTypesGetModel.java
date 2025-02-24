package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.util.List;

/**
 * Meta-entity for GET ticket group seating types.
 */
public class SeatingTypesGetModel implements Serializable {

	private static final long serialVersionUID = 2025022501L;

	/**
	 * The collection of SeatingType objects.
	 */
	private List<SeatingType> seatingTypes;

	public List<SeatingType> getSeatingTypes() {
		return seatingTypes;
	}

	public void setSeatingTypes(List<SeatingType> seatingTypes) {
		this.seatingTypes = seatingTypes;
	}

}
