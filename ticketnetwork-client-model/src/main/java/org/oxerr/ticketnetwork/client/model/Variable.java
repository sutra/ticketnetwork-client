package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

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

}
