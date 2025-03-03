package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Variable implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The variable that is being replaced in the note, e.g. "@@A@@".
	 */
	private String variable;

	/**
	 * The value for the variable.
	 */
	private Integer value;

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
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
		if (!(obj instanceof Variable)) {
			return false;
		}
		Variable rhs = (Variable) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
