package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The HTTP links for the resource; links are used to transition through
 * application states by the client and identify related resources available
 * for interaction.
 */
public class Link implements Serializable {

	private static final long serialVersionUID = 2025022201L;

	/**
	 * The URI of the link.
	 */
	private String href;

	/**
	 * The string representation of the set of values identifying the
	 * relationship between the current resource and the linked resource.
	 * Each individual relationship identifier is separated by a space.
	 */
	private String rel;

	/**
	 * The media type of the linked resource.
	 */
	private String type;

	/**
	 * The language of the linked resource.
	 */
	private String hreflang;

	/**
	 * A natural language title of the resource, which conveys human-readable
	 * information about the link.
	 */
	private String title;

	/**
	 * The general length of the linked content in octets.
	 */
	private Long length;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHreflang() {
		return hreflang;
	}

	public void setHreflang(String hreflang) {
		this.hreflang = hreflang;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
