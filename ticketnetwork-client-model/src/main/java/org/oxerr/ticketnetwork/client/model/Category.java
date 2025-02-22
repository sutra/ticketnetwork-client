package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

public class Category implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The ID of the category.
	 */
	private Integer id;

	/**
	 * A description of the category.
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

}
