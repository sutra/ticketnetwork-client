package org.oxerr.ticketnetwork.client.rescu.resource;

import java.util.Map;

import org.oxerr.ticketnetwork.client.model.ValidationErrorMember;
import org.oxerr.ticketnetwork.client.model.ValidationErrorsModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import si.mazi.rescu.HttpStatusException;

/**
 * The model used to encapsulate validation errors.
 */
public class TicketNetworkException extends ValidationErrorsModel implements HttpStatusException {

	private static final long serialVersionUID = 2025022401L;

	@JsonIgnore
	private int __httpStatusCode;

	public TicketNetworkException(
		@JsonProperty("message") String message,
		@JsonProperty("validationErrors") Map<String, ValidationErrorMember> validationErrors
	) {
		super(message, validationErrors);
	}

	@Override
	@JsonIgnore
	public StackTraceElement[] getStackTrace() {
		return super.getStackTrace();
	}

	@Override
	public String getMessage() {
		return String.format("%s (HTTP status code: %d)", super.getMessage(), __httpStatusCode);
	}

	public int getHttpStatusCode() {
		return __httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.__httpStatusCode = httpStatusCode;
	}

}
