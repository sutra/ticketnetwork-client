package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

public class StockType implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The stock type for the ticket group. PATCH operations: add, replace, test.
	 */
	private Integer id;

	/**
	 * Description of the stock type.
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
