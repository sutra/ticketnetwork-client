package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

public class AdditionalNoteClass implements Serializable {

	private static final long serialVersionUID = 2025061201L;

	/**
	 * The additional note class ID.
	 */
	private Integer id;

	/**
	 * The class description.
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
