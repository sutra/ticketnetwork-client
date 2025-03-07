package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.time.Instant;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ThirdPartyExchangeListing implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The third party exchange id.
	 */
	private Integer thirdPartyExchangeId;

	/**
	 * The third party exchange name.
	 */
	private String thirdPartyExchangeName;

	/**
	 * The third party exchange listing id.
	 */
	private Long thirdPartyExchangeListingId;

	/**
	 * The third party exchange listing status.
	 */
	private Integer thirdPartyExchangeListingStatus;

	/**
	 * The last time the status of the third party listing was checked.
	 */
	private Instant checkedAtDateTime;

	/**
	 * An error message from the third party exchange, if any.
	 */
	private String thirdPartyExchangeMessage;

	public Integer getThirdPartyExchangeId() {
		return thirdPartyExchangeId;
	}

	public void setThirdPartyExchangeId(Integer thirdPartyExchangeId) {
		this.thirdPartyExchangeId = thirdPartyExchangeId;
	}

	public String getThirdPartyExchangeName() {
		return thirdPartyExchangeName;
	}

	public void setThirdPartyExchangeName(String thirdPartyExchangeName) {
		this.thirdPartyExchangeName = thirdPartyExchangeName;
	}

	public Long getThirdPartyExchangeListingId() {
		return thirdPartyExchangeListingId;
	}

	public void setThirdPartyExchangeListingId(Long thirdPartyExchangeListingId) {
		this.thirdPartyExchangeListingId = thirdPartyExchangeListingId;
	}

	public Integer getThirdPartyExchangeListingStatus() {
		return thirdPartyExchangeListingStatus;
	}

	public void setThirdPartyExchangeListingStatus(Integer thirdPartyExchangeListingStatus) {
		this.thirdPartyExchangeListingStatus = thirdPartyExchangeListingStatus;
	}

	public Instant getCheckedAtDateTime() {
		return checkedAtDateTime;
	}

	public void setCheckedAtDateTime(Instant checkedAtDateTime) {
		this.checkedAtDateTime = checkedAtDateTime;
	}

	public String getThirdPartyExchangeMessage() {
		return thirdPartyExchangeMessage;
	}

	public void setThirdPartyExchangeMessage(String thirdPartyExchangeMessage) {
		this.thirdPartyExchangeMessage = thirdPartyExchangeMessage;
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
		if (!(obj instanceof ThirdPartyExchangeListing)) {
			return false;
		}
		ThirdPartyExchangeListing rhs = (ThirdPartyExchangeListing) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
