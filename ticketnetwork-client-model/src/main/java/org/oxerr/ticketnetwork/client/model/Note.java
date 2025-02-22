package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.util.List;

public class Note implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The ID for the additional note.
	 */
	private Integer id;

	/**
	 * The text of the additonal note, without variable replacement.
	 */
	private String description;

	/**
	 * Collection of additonal note variable objects.
	 */
	private List<Variable> variables;

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

	public List<Variable> getVariables() {
		return variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}

}
