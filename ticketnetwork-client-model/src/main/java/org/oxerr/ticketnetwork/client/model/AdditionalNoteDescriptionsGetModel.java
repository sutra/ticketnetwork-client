package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.util.List;

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

}
