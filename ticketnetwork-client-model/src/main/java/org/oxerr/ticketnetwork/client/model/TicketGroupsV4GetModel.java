package org.oxerr.ticketnetwork.client.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TicketGroupsV4GetModel implements Serializable {

	private static final long serialVersionUID = 2015022201L;

	/**
	 * Collection of TicketGroup objects.
	 */
	private List<TicketGroup> results;

	/**
	 * The total number of results. May be larger than the page size.
	 */
	private Integer totalCount;

	/**
	 * The page number.
	 */
	private Integer page;

	/**
	 * The number of records on this page.
	 */
	private Integer count;

	/**
	 * The HTTP links for the resource; links are used to transition through
	 * application states by the client and identify related resources
	 * available for interaction.
	 */
	@JsonProperty("_links")
	private List<Link> links;

	public List<TicketGroup> getResults() {
		return results;
	}

	public void setResults(List<TicketGroup> results) {
		this.results = results;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

}
