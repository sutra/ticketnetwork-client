package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Meta-model for GET additional notes.
 */
public class AdditionalNoteDescriptionsGetModel implements Serializable {

	private static final long serialVersionUID = 2025061201L;

	/**
	 * The collection of {@link AdditionalNoteDesc} objects.
	 */
	private List<AdditionalNoteDescGetModel> additionalNotes;

	public List<AdditionalNoteDescGetModel> getAdditionalNotes() {
		return additionalNotes;
	}

	public void setAdditionalNotes(List<AdditionalNoteDescGetModel> additionalNotes) {
		this.additionalNotes = additionalNotes;
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
		if (!(obj instanceof AdditionalNoteDescriptionsGetModel)) {
			return false;
		}
		AdditionalNoteDescriptionsGetModel rhs = (AdditionalNoteDescriptionsGetModel) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
