package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NearTermDisplay implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * Indicates when the near-term shipping method(s) should become available
	 * to customers. PATCH operations: add, replace, test.
	 */
	private Integer id;

	/**
	 * Description of the near-term display option.
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
