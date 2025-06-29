package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

	public Note() {
	}

	public Note(Integer id, String description) {
		this.id = id;
		this.description = description;
	}

	public Note(Integer id, String description, List<Variable> variables) {
		this.id = id;
		this.description = description;
		this.variables = variables;
	}

	public Note(AdditionalNotePostModel model) {
		this.id = model.getNoteId();
		this.variables = Optional.ofNullable(model.getVariables())
			.orElseGet(List::of)
			.stream()
			.map(Variable::new)
			.collect(Collectors.toList());
	}

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

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Note)) {
			return false;
		}
		Note rhs = (Note) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
