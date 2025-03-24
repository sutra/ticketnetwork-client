package org.oxerr.ticketnetwork.client.inventory;

public class AllTicketGroupQuery {

	private Boolean hasEticket;

	private Boolean pending;

	private Boolean includeTicketNetworkInventory;

	private Integer returnTicketsData;

	private Integer perPage;

	private Integer page;

	private Integer skip;

	private String filter;

	private String orderby;

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

	public Boolean getIncludeTicketNetworkInventory() {
		return includeTicketNetworkInventory;
	}

	public void setIncludeTicketNetworkInventory(Boolean includeTicketNetworkInventory) {
		this.includeTicketNetworkInventory = includeTicketNetworkInventory;
	}

	public Integer getReturnTicketsData() {
		return returnTicketsData;
	}

	public void setReturnTicketsData(Integer returnTicketsData) {
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
