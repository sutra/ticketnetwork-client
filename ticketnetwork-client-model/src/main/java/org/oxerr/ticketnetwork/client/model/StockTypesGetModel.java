package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Meta-entity for GET ticket group stock types.
 */
public class StockTypesGetModel implements Serializable {

	private static final long serialVersionUID = 2025041701L;

	/**
	 * The collection of StockType objects.
	 */
	private List<StockType> stockTypes;

	public List<StockType> getStockTypes() {
		return stockTypes;
	}

	public void setStockTypes(List<StockType> stockTypes) {
		this.stockTypes = stockTypes;
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
		if (!(obj instanceof StockTypesGetModel)) {
			return false;
		}
		StockTypesGetModel rhs = (StockTypesGetModel) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
