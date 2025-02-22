package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

public class Quantity implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The total number of tickets in the ticket group. PATCH operations: replace.
	 */
	private Integer total;

	/**
	 * The number of available tickets in the ticket group.
	 */
	private Integer available;

	/**
	 * The number of held tickets in the ticket group.
	 */
	private Integer held;

	/**
	 * The number of sold tickets in the ticket group.
	 */
	private Integer sold;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public Integer getHeld() {
		return held;
	}

	public void setHeld(Integer held) {
		this.held = held;
	}

	public Integer getSold() {
		return sold;
	}

	public void setSold(Integer sold) {
		this.sold = sold;
	}

}
