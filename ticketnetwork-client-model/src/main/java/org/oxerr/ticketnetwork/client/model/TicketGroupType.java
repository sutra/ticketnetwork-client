package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TicketGroupType implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The ticket group type for the ticket group. PATCH operations: add,
	 * replace, test.
	 */
	private Integer id;

	/**
	 * Description of the ticket group type.
	 */
	private String description;

	/**
	 * Flag indicating whether or not custom notes will appear on websites.
	 */
	private Boolean allowsCustomNotes;

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

	public Boolean getAllowsCustomNotes() {
		return allowsCustomNotes;
	}

	public void setAllowsCustomNotes(Boolean allowsCustomNotes) {
		this.allowsCustomNotes = allowsCustomNotes;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
