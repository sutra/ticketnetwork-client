package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Seats implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The broker-entered section in which the ticket group is located. Max length
	 * is 100 characters. PATCH operations: add, replace, test.
	 */
	private String section;

	/**
	 * The row in which the ticket group is located. Max length is 30 characters.
	 * PATCH operations: add, replace, test.
	 */
	private String row;

	/**
	 * The standard section name, if available. The value is null if the ticket
	 * group has not been matched to a standard section. Max length is 100
	 * characters. PATCH operations: add, replace, test.
	 */
	private String standardSection;

	/**
	 * Gets or sets the canonical section, if available. The value is null if the
	 * ticket group has not been matched to a canonical section. Max length is 500
	 * characters. PATCH operations: add, replace, test.
	 */
	private String canonicalSection;

	/**
	 * The level code corresponding to the canonical section of this ticket group.
	 */
	private String levelCode;

	/**
	 * The seating type. Possible values are “Consecutive”, “Odd/Even” (odd or even
	 * seat numbers), and “General Admission”.
	 */
	private SeatingType seatingType;

	/**
	 * The low seat number in the group. When seatingType is General Admission, the
	 * value is null. PATCH operations: add, replace, test.
	 */
	private Integer lowSeat;

	/**
	 * The high seat number in the group. When seatingType is General Admission, the
	 * value is null.
	 */
	private Integer highSeat;

	/**
	 * The available low seat number in the group.
	 */
	private Integer availableLowSeat;

	/**
	 * The available high seat number in the group.
	 */
	private Integer availableHighSeat;

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public String getStandardSection() {
		return standardSection;
	}

	public void setStandardSection(String standardSection) {
		this.standardSection = standardSection;
	}

	public String getCanonicalSection() {
		return canonicalSection;
	}

	public void setCanonicalSection(String canonicalSection) {
		this.canonicalSection = canonicalSection;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public SeatingType getSeatingType() {
		return seatingType;
	}

	public void setSeatingType(SeatingType seatingType) {
		this.seatingType = seatingType;
	}

	public Integer getLowSeat() {
		return lowSeat;
	}

	public void setLowSeat(Integer lowSeat) {
		this.lowSeat = lowSeat;
	}

	public Integer getHighSeat() {
		return highSeat;
	}

	public void setHighSeat(Integer highSeat) {
		this.highSeat = highSeat;
	}

	public Integer getAvailableLowSeat() {
		return availableLowSeat;
	}

	public void setAvailableLowSeat(Integer availableLowSeat) {
		this.availableLowSeat = availableLowSeat;
	}

	public Integer getAvailableHighSeat() {
		return availableHighSeat;
	}

	public void setAvailableHighSeat(Integer availableHighSeat) {
		this.availableHighSeat = availableHighSeat;
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
		if (!(obj instanceof Seats)) {
			return false;
		}
		Seats rhs = (Seats) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
