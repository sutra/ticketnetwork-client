package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SplitRule implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The split rule id. PATCH operations: add, replace, test.
	 */
	private Integer id;

	/**
	 * The split rule description.
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
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
