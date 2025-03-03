package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A validation error member which describes one or more reasons for validation
 * failure.
 */
public class ValidationErrorMember implements Serializable {

	private static final long serialVersionUID = 2025022301L;

	/**
	 * A collection of one or more reasons as to why validation failed for the
	 * corresponding field or property.
	 */
	private List<String> reasons;

	public List<String> getReasons() {
		return reasons;
	}

	public void setReasons(List<String> reasons) {
		this.reasons = reasons;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
