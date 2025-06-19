package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Model for additional notes for POST requests.
 */
public class AdditionalNotePostModel implements Serializable {

	private static final long serialVersionUID = 2025061901L;

	/**
	 * The ID for the additional note.
	 */
	private Integer noteId;

	/**
	 * Collection of additional note variable objects.
	 */
	private List<AdditionalNoteVariablePostModel> variables;

	public AdditionalNotePostModel() {
	}

	public AdditionalNotePostModel(Integer noteId) {
		this.noteId = noteId;
	}

	public AdditionalNotePostModel(Integer noteId,
			List<AdditionalNoteVariablePostModel> variables) {
		this.noteId = noteId;
		this.variables = Optional.ofNullable(variables)
			.orElseGet(Collections::emptyList);
	}

	public AdditionalNotePostModel(Note note) {
		this.noteId = note.getId();
		this.variables = Optional.ofNullable(note.getVariables())
			.orElseGet(Collections::emptyList)
			.stream()
			.map(AdditionalNoteVariablePostModel::new)
			.collect(Collectors.toList());
	}

	public Integer getNoteId() {
		return noteId;
	}

	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}

	public List<AdditionalNoteVariablePostModel> getVariables() {
		return variables;
	}

	public void setVariables(List<AdditionalNoteVariablePostModel> variables) {
		this.variables = variables;
	}

}
