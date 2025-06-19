package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Model for notes for POST requests.
 */
public class NotesPostModel implements Serializable {

	private static final long serialVersionUID = 2025061901;

	/**
	 * External/custom ticket group notes. Max length is 2,000 characters.
	 */
	private String external;

	/**
	 * The broker's internal ticket group notes. Max length is 2,000 characters.
	 */
	private String internal;

	/**
	 * Collection of additional note objects.
	 */
	private List<AdditionalNotePostModel> additional;

	public NotesPostModel() {
	}

	public NotesPostModel(Notes notes) {
		this.external = notes.getExternal();
		this.internal = notes.getInternal();

		this.additional = Optional.ofNullable(notes.getAdditional())
			.orElseGet(Collections::emptyList)
			.stream()
			.map(AdditionalNotePostModel::new)
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

	public List<AdditionalNotePostModel> getAdditional() {
		return additional;
	}

	public void setAdditional(List<AdditionalNotePostModel> additional) {
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
		if (!(obj instanceof NotesPostModel)) {
			return false;
		}
		NotesPostModel rhs = (NotesPostModel) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
