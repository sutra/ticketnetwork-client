package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * Gets or sets the user ID.
	 */
	private Integer userId;

	/**
	 * Gets or sets the name of the user.
	 */
	private String name;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
