package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

public class Tag implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The text of the tag. Max length is 50 characters.
	 */
	private String tag;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
