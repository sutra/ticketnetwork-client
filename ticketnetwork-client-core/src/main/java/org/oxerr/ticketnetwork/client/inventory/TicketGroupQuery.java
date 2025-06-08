package org.oxerr.ticketnetwork.client.inventory;

public class TicketGroupQuery {

	Boolean hasEticket;

	Boolean pending;

	Boolean returnTicketsData;

	/**
	 * The number of results per page.
	 *
	 * A maximum of 500 results will be returned, even if a value greater than 500 is specified.
	 */
	Integer perPage;

	Integer page;

	Integer skip;

	String filter;

	String orderby;

	public Boolean getHasEticket() {
		return hasEticket;
	}

	public void setHasEticket(Boolean hasEticket) {
		this.hasEticket = hasEticket;
	}

	public Boolean getPending() {
		return pending;
	}

	public void setPending(Boolean pending) {
		this.pending = pending;
	}

	public Boolean getReturnTicketsData() {
		return returnTicketsData;
	}

	public void setReturnTicketsData(Boolean returnTicketsData) {
		this.returnTicketsData = returnTicketsData;
	}

	public Integer getPerPage() {
		return perPage;
	}

	public void setPerPage(Integer perPage) {
		this.perPage = perPage;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSkip() {
		return skip;
	}

	public void setSkip(Integer skip) {
		this.skip = skip;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

}
