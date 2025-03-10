package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The model used to encapsulate validation errors.
 */
public class ValidationErrorsModel extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 2025022301L;

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
	private final Map<String, ValidationErrorMember> validationErrors;

	/**
	 * Constructs a new instance of {@link ValidationErrorsModel}.
	 *
	 * @param message The top-level error message.
	 * @param validationErrors The validation errors that apply to specific
	 * fields or properties in the request.
	 */
	public ValidationErrorsModel(
		@JsonProperty("message") String message,
		@JsonProperty("validationErrors") Map<String, ValidationErrorMember> validationErrors
	) {
		super(message);
		this.validationErrors = validationErrors;
	}

	public Map<String, ValidationErrorMember> getValidationErrors() {
		return validationErrors;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
			.append(validationErrors)
			.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ValidationErrorsModel)) {
			return false;
		}
		ValidationErrorsModel rhs = (ValidationErrorsModel) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("validationErrors", validationErrors)
			.toString();
	}

}
