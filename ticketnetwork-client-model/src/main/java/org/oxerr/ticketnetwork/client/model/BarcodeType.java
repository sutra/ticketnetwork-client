package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

public class BarcodeType implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The barcode type for the ticket group.
	 */
	private Integer id;

	/**
	 * Description of the barcode type.
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
