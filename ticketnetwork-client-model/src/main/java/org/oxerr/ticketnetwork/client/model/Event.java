package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Event implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The ID of the event for this listing. PATCH operations: add, replace, test.
	 */
	private Integer id;

	/**
	 * The name of the event.
	 */
	private String name;

	/**
	 * The date of the event.
	 */
	private OffsetDateTime date;

	/**
	 * The time of the event. When the value is null, the start Time is TBD.
	 */
	private LocalTime time;

	/**
	 * The date of the event.
	 */
	private OffsetDateTime dateTime;

	/**
	 * Venue object.
	 */
	private Venue venue;

	/**
	 * The array of the category IDs in the event.
	 */
	private List<Category> categories;

	/**
	 * The URL of the static map for the event's venue configuration.
	 */
	private String staticMapUrl;

	/**
	 * The venue configuration Id.
	 */
	private Integer venueConfigurationId;

	public Event() {
	}

	public Event(Integer id) {
		this.id = id;
	}

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

	public OffsetDateTime getDate() {
		return date;
	}

	public void setDate(OffsetDateTime date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public OffsetDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(OffsetDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public String getStaticMapUrl() {
		return staticMapUrl;
	}

	public void setStaticMapUrl(String staticMapUrl) {
		this.staticMapUrl = staticMapUrl;
	}

	public Integer getVenueConfigurationId() {
		return venueConfigurationId;
	}

	public void setVenueConfigurationId(Integer venueConfigurationId) {
		this.venueConfigurationId = venueConfigurationId;
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
		if (!(obj instanceof Event)) {
			return false;
		}
		Event rhs = (Event) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
