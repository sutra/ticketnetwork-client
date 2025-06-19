package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AdditionalNoteDesc implements Serializable {

	private static final long serialVersionUID = 2025061201L;

	/**
	 * The ID of the additional note description.
	 */
	private Integer id;

	/**
	 * The additional note description.
	 */
	private String description;

	/**
	 * The ID of the additional note class.
	 */
	private Integer classId;

	/**
	 * A value indicating whether the note is active.
	 */
	private Boolean active;

	/**
	 * A value indicating whether [variable enabled].
	 */
	private Boolean variableEnabled;

	/**
	 * A value indicating whether this note is required to be able to list on
	 * external exchanges.
	 */
	private Boolean isRequired;

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

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getVariableEnabled() {
		return variableEnabled;
	}

	public void setVariableEnabled(Boolean variableEnabled) {
		this.variableEnabled = variableEnabled;
	}

	public Boolean getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
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
		if (!(obj instanceof AdditionalNoteDesc)) {
			return false;
		}
		AdditionalNoteDesc rhs = (AdditionalNoteDesc) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
