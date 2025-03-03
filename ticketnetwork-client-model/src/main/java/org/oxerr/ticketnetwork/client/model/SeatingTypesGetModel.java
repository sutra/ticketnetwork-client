package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SeatingTypesGetModel)) {
			return false;
		}
		SeatingTypesGetModel rhs = (SeatingTypesGetModel) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
