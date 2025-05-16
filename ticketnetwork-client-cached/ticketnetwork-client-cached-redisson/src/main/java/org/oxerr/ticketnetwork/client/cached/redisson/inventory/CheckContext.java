package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.oxerr.ticketnetwork.client.cached.inventory.CheckOptions;
import org.oxerr.ticketnetwork.client.inventory.TicketGroupQuery;
import org.oxerr.ticketnetwork.client.model.TicketGroupsV4GetModel;

class CheckContext {

	private final CheckOptions options;

	/**
	 * A map to reflect the cached info by marketplace listing info.
	 */
	private final Map<ListingInfo, CacheInfo> caches;

	/**
	 * The ticket group IDs listed on the marketplace.
	 */
	private final Set<Integer> listedTicketGroupIds;

	/**
	 * The checking tasks.
	 */
	private final List<CompletableFuture<TicketGroupsV4GetModel>> checkings;

	/**
	 * The tasks to delete or update the listings.
	 */
	private final List<CompletableFuture<Void>> tasks;

	private final AtomicInteger skip;

	public CheckContext(CheckOptions options, Map<ListingInfo, CacheInfo> caches) {
		this.options = options;
		this.caches = caches;
		this.listedTicketGroupIds = ConcurrentHashMap.newKeySet();
		this.checkings = Collections.synchronizedList(new ArrayList<>());
		this.tasks = Collections.synchronizedList(new ArrayList<>());
		this.skip = new AtomicInteger();
	}

	public CheckOptions getOptions() {
		return options;
	}

	public Map<ListingInfo, CacheInfo> getCaches() {
		return caches;
	}

	public TicketGroupQuery nextPage() {
		TicketGroupQuery q = new TicketGroupQuery();
		q.setPerPage(options.pageSize());
		q.setSkip(skip.getAndAdd(options.pageSize()));
		return q;
	}

	public int checkingCount() {
		return checkings.size();
	}

	public boolean addChecking(CompletableFuture<TicketGroupsV4GetModel> e) {
		return checkings.add(e);
	}

	public void joinCheckings() {
		CompletableFuture.allOf(checkings.toArray(CompletableFuture[]::new)).join();
	}

	public int taskCount() {
		return tasks.size();
	}

	public boolean addTask(CompletableFuture<Void> e) {
		return tasks.add(e);
	}

	public boolean addTasks(Collection<? extends CompletableFuture<Void>> c) {
		return tasks.addAll(c);
	}

	public void joinTasks() {
		CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new)).join();
	}

	/**
	 * Adds ticket group IDs which is listed on the marketplace.
	 *
	 * @param ticketGroupId the ticket group ID.
	 */
	public void addListedTicketGroupId(Integer ticketGroupId) {
		listedTicketGroupIds.add(ticketGroupId);
	}

	/**
	 * Returns the missing ticket group informations on the marketplace.
	 *
	 * @return the missing ticket group informations.
	 */
	public Set<CacheInfo> getMissingTicketGroupInfos() {
		// missing = cached - listed
		return caches.values().stream()
			.filter(entry -> entry.getTicketGroupId() == null || !listedTicketGroupIds.contains(entry.getTicketGroupId()))
			.collect(Collectors.toSet());
	}

}
