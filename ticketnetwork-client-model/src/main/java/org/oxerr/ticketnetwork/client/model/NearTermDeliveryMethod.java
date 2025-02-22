package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

public class NearTermDeliveryMethod implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The near term shipping method or set of near term shipping methods that
	 * should be available to customers. PATCH operations: add, replace, test.
	 */
	private Integer id;

	/**
	 * Description of the near-term shipping method.
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
