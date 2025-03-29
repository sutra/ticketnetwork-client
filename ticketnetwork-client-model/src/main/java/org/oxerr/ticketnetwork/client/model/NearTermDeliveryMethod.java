package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Model for near term delivery method.
 */
public class NearTermDeliveryMethod implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The near term shipping method or set of near term shipping methods that
	 * should be available to customers. PATCH operations: add, replace, test.
	 */
	private Integer id;

	/**
	 * Description of the near-term shipping method.
	 */
	private String description;

	public NearTermDeliveryMethod() {
	}

	public NearTermDeliveryMethod(Integer id) {
		this.id = id;
	}

	/**
	 * Returns the ID.
	 *
	 * @return the ID.
	 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Returns the description.
	 *
	 * @return the description.
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		if (!(obj instanceof NearTermDeliveryMethod)) {
			return false;
		}
		NearTermDeliveryMethod rhs = (NearTermDeliveryMethod) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
