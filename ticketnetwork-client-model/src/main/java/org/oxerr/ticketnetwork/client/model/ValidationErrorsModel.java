package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.util.Map;

/**
 * The model used to encapsulate validation errors.
 */
public class ValidationErrorsModel implements Serializable {

	private static final long serialVersionUID = 2025022301L;

	/**
	 * The top-level error message.
	 */
	private String message;

	/**
	 * The validation errors that apply to specific fields or properties in the
	 * request. The dictionary is keyed by the field or property that generated
	 * the validation error.
	 *
	 * A key/value pair that contains details on the validation errors that
	 * occurred for the corresponding field or property. 'fieldName' will match
	 * the name of the field or property that failed validation on the original
	 * model. Validation errors that don't apply to a specific field or
	 * property on the model will be keyed to the empty string.
	 */
	private Map<String, ValidationErrorMember> validationErrors;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, ValidationErrorMember> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(Map<String, ValidationErrorMember> validationErrors) {
		this.validationErrors = validationErrors;
	}

}
