package org.oxerr.ticketnetwork.client.model;

/**
 * Model for additional note variable for POST requests.
 */
public class AdditionalNoteVariablePostModel extends Variable {

	private static final long serialVersionUID = 2025061901L;

	public AdditionalNoteVariablePostModel() {
		super();
	}

	public AdditionalNoteVariablePostModel(String variable, Integer value) {
		super(variable, value);
	}

	public AdditionalNoteVariablePostModel(Variable variable) {
		super(variable.getVariable(), variable.getValue());
	}

}
