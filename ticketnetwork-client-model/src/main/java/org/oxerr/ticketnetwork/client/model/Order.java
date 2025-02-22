package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

public class Order implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * ID for the order.
	 */
	private Integer orderId;

	/**
	 * ID for the order type.
	 */
	private Integer orderTypeId;

	/**
	 * Description of the order type (e.g. ticket request or Mercury transaction).
	 */
	private String orderType;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(Integer orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

}
