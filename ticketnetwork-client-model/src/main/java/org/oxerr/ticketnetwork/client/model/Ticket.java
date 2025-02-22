package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

public class Ticket implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The ID for the ticket.
	 */
	private Integer id;

	/**
	 * The seat number for the ticket.
	 */
	private Integer seat;

	/**
	 * The barcode for the ticket. PATCH operations: add, remove, replace, test.
	 */
	private String barcode;

	/**
	 * The barcode type for the ticket.
	 */
	private BarcodeType barcodeType;

	/**
	 * Link to eticket resource for the ticket.
	 */
	private Link eticket;

	/**
	 * The status of the ticket. Possible values: available, sold, on hold.
	 */
	private String status;

	/**
	 * The order id related to this ticket.
	 */
	private Long orderId;

	/**
	 * The purchase order id related to this ticket.
	 */
	private Integer purchaseOrderId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSeat() {
		return seat;
	}

	public void setSeat(Integer seat) {
		this.seat = seat;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public BarcodeType getBarcodeType() {
		return barcodeType;
	}

	public void setBarcodeType(BarcodeType barcodeType) {
		this.barcodeType = barcodeType;
	}

	public Link getEticket() {
		return eticket;
	}

	public void setEticket(Link eticket) {
		this.eticket = eticket;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Integer purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

}
