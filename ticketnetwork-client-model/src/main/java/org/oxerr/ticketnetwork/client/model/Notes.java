package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Notes implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * External/custom ticket group notes. Max length is 2,000 characters.
	 * PATCH operations: add, remove, replace, test.
	 */
	private String external;

	/**
	 * The broker's internal ticket group notes. Max length is 2,000 characters.
	 * PATCH operations: add, remove, replace, test.
	 */
	private String internal;

	/**
	 * Collection of additional notes objects. PATCH operations: add, remove,
	 * replace, test.
	 */
	private List<Note> additional;

	public Notes() {
	}

	public Notes(NotesPostModel model) {
		this.external = model.getExternal();
		this.internal = model.getInternal();
		this.additional = Optional.ofNullable(model.getAdditional())
			.orElseGet(List::of)
			.stream()
			.map(Note::new)
			.collect(Collectors.toList());
	}

	public String getExternal() {
		return external;
	}

	public void setExternal(String external) {
		this.external = external;
	}

	public String getInternal() {
		return internal;
	}

	public void setInternal(String internal) {
		this.internal = internal;
	}

	public List<Note> getAdditional() {
		return additional;
	}

	public void setAdditional(List<Note> additional) {
		this.additional = additional;
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
		if (!(obj instanceof Notes)) {
			return false;
		}
		Notes rhs = (Notes) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
