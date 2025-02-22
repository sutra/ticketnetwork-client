package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

public class Venue implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The venue ID of the event.
	 */
	private Integer id;

	/**
	 * The name of the venue.
	 */
	private String name;

	/**
	 * The address of the venue.
	 */
	private Address address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
