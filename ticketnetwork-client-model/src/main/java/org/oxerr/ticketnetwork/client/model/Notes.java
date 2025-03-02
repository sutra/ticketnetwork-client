package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Notes implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * External/custom ticket group notes. Max length is 2,000 characters.
	 * PATCH operations: add, remove, replace, test.
	 */
	private String external;

	/**
	 * The broker's internal ticket group notes. Max length is 2,000 characters.
	 * PATCH operations: add, remove, replace, test.
	 */
	private String internal;

	/**
	 * Collection of additional notes objects. PATCH operations: add, remove,
	 * replace, test.
	 */
	private List<Note> additional;

	public String getExternal() {
		return external;
	}

	public void setExternal(String external) {
		this.external = external;
	}

	public String getInternal() {
		return internal;
	}

	public void setInternal(String internal) {
		this.internal = internal;
	}

	public List<Note> getAdditional() {
		return additional;
	}

	public void setAdditional(List<Note> additional) {
		this.additional = additional;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
