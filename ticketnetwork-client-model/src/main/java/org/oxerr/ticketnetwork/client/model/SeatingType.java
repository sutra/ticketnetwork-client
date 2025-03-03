package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The seating type.
 * Possible values are “Consecutive”, “Odd/Even” (odd or even seat numbers),
 * and “General Admission”.
 */
public class SeatingType implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The seating type for the ticket group. PATCH operations: add, replace, test.
	 */
	private Integer id;

	/**
	 * Description of the seating type.
	 */
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		if (!(obj instanceof SeatingType)) {
			return false;
		}
		SeatingType rhs = (SeatingType) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
