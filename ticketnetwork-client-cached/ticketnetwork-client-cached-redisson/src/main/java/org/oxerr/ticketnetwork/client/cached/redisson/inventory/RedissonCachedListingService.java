package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.oxerr.ticket.inventory.support.cached.redisson.ListingConfiguration;
import org.oxerr.ticket.inventory.support.cached.redisson.RedissonCachedListingServiceSupport;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;
import org.oxerr.ticketnetwork.client.cached.inventory.CheckOptions;
import org.oxerr.ticketnetwork.client.cached.inventory.TicketNetworkCachedListingService;
import org.oxerr.ticketnetwork.client.cached.inventory.TicketNetworkEvent;
import org.oxerr.ticketnetwork.client.cached.inventory.TicketNetworkListing;
import org.oxerr.ticketnetwork.client.inventory.InventoryService;
import org.oxerr.ticketnetwork.client.inventory.TicketGroupQuery;
import org.oxerr.ticketnetwork.client.model.TicketGroup;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;
import org.oxerr.ticketnetwork.client.model.TicketGroupsV4GetModel;
import org.oxerr.ticketnetwork.client.model.ValidationErrorsModel;
import org.redisson.api.RedissonClient;

public class RedissonCachedListingService
	extends RedissonCachedListingServiceSupport<
		String,
		String,
		TicketGroupV4PostModel,
		TicketNetworkListing,
		TicketNetworkEvent,
		TicketNetworkCachedListing
	>
	implements TicketNetworkCachedListingService {

	private final Logger log = LogManager.getLogger();

	private final InventoryService inventoryService;

	/**
	 * Constructs with default {@link ListingConfiguration}.
	 *
	 * @param inventoryService the inventory service.
	 * @param redissonClient the redisson client.
	 * @param keyPrefix the key prefix for the cache.
	 * @param executor the executor.
	 */
	public RedissonCachedListingService(
		InventoryService inventoryService,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor
	) {
		this(inventoryService, redissonClient, keyPrefix, executor, new ListingConfiguration());
	}

	/**
	 * Constructs with specified {@link ListingConfiguration}.
	 *
	 * @param inventoryService the inventory service.
	 * @param redissonClient the redisson client.
	 * @param keyPrefix the key prefix for the cache.
	 * @param executor the executor.
	 * @param listingConfiguration the listing configuration.
	 */
	public RedissonCachedListingService(
		InventoryService inventoryService,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor,
		ListingConfiguration listingConfiguration
	) {
		super(redissonClient, keyPrefix, executor, listingConfiguration);
		this.inventoryService = inventoryService;
	}

	@Override
	protected boolean shouldCreate(
		@Nonnull TicketNetworkEvent event,
		@Nonnull TicketNetworkListing ticketGroup,
		@Nullable TicketNetworkCachedListing cachedListing
	) {
		boolean shouldCreate = super.shouldCreate(event, ticketGroup, cachedListing);
		return shouldCreate || (cachedListing != null && !Objects.equals(ticketGroup.getMarketplaceEventId(), cachedListing.getEvent().getMarketplaceEventId()));
	}

	@Override
	protected boolean shouldUpdate(
		@Nonnull TicketNetworkEvent event,
		@Nonnull TicketNetworkListing ticketGroup,
		@Nullable TicketNetworkCachedListing cachedListing
	) {
		boolean shouldUpdate = super.shouldUpdate(event, ticketGroup, cachedListing);
		return shouldUpdate || (cachedListing != null && !Objects.equals(ticketGroup.getMarketplaceEventId(), cachedListing.getEvent().getMarketplaceEventId()));
	}

	@Override
	protected int getPriority(
		@Nonnull TicketNetworkEvent event,
		@Nullable TicketNetworkListing ticketGroup,
		@Nullable TicketNetworkCachedListing cachedListing
	) {
		if (ticketGroup == null || cachedListing == null) {
			return 0;
		}

		int priority = 0;

		var r = ticketGroup.getRequest();
		var cr = cachedListing.getRequest();

		priority += Objects.equals(r.getQuantity(), cr.getQuantity()) ? 0 : 1;
		priority += Objects.equals(r.getSection(), cr.getSection()) ? 0 : 1;
		priority += Objects.equals(r.getRow(), cr.getRow()) ? 0 : 1;
		priority += Objects.equals(r.getLowSeat(), cr.getLowSeat()) ? 0 : 1;
		priority += Objects.equals(r.getHideSeats(), cr.getHideSeats()) ? 0 : 1;
		priority += Objects.equals(r.getNotes(), cr.getNotes()) ? 0 : 1;

		return priority;
	}

	@Override
	protected boolean shouldDelete(
		@Nonnull TicketNetworkEvent event,
		@Nonnull Set<String> inventoryListingIds,
		@Nonnull String listingId,
		@Nonnull TicketNetworkCachedListing cachedListing
	) {
		return super.shouldDelete(event, inventoryListingIds, listingId, cachedListing)
			|| !Objects.equals(event.getMarketplaceEventId(), cachedListing.getEvent().getMarketplaceEventId());
	}

	@Override
	protected void createListing(TicketNetworkEvent event, TicketNetworkListing listing) throws IOException {
		TicketGroup ticketGroup;

		try {
			ticketGroup = inventoryService.createTicketGroup(listing.getRequest());
		} catch (ValidationErrorsModel e) {
			if (e.getValidationErrors().get("lowSeat") != null
				&& e.getValidationErrors().get("lowSeat").getReasons()
					.contains("A ticket group already exists with the provided event ID, section, and row with overlapping seats.")) {

				var filter = String.format("event/id eq %d and seats/section eq '%s' and seats/row eq '%s'",
					event.getMarketplaceEventId(), listing.getRequest().getSection(), listing.getRequest().getRow());

				TicketGroupQuery q = new TicketGroupQuery();
				q.setFilter(filter);
				TicketGroupsV4GetModel ticketGroups = inventoryService.getTicketGroups(q);
				log.info("Filter: {}, ticket group count: {}", filter, ticketGroups.getTotalCount());

				if (ticketGroups.getTotalCount() > 0) {
					TicketGroup existing = ticketGroups.getResults().get(0);
					if (existing.getReferenceTicketGroupId().equals(listing.getRequest().getReferenceTicketGroupId())) {
						ticketGroup = existing;
					} else {
						throw e;
					}
				} else {
					throw e;
				}
			} else {
				throw e;
			}
		}

		// Update ticket group ID in cache
		TicketNetworkCachedListing cached = getEventCache(event.getId()).get(listing.getId());
		cached.setTicketGroupId(ticketGroup.getTicketGroupId());

		getEventCache(event.getId()).put(listing.getId(), cached);
	}

	@Override
	protected void updateListing(
		TicketNetworkEvent event,
		TicketNetworkListing listing,
		TicketNetworkCachedListing cachedListing,
		int priority
	) throws IOException {
		Integer ticketGroupId = cachedListing.getTicketGroupId();
		TicketGroupV4PostModel target = listing.getRequest();
		TicketGroupV4PostModel source = cachedListing.getRequest();
		inventoryService.updateTicketGroup(ticketGroupId, target, source);
	}

	@Override
	protected void deleteListing(
		TicketNetworkEvent event,
		String listingId,
		TicketNetworkCachedListing cachedListing,
		int priority
	) throws IOException {
		if (cachedListing.getTicketGroupId() != null) {
			inventoryService.deleteTicketGroup(cachedListing.getTicketGroupId());
		}
	}

	@Override
	protected void deleteListing(TicketNetworkEvent event, String listingId) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	protected TicketNetworkCachedListing toCached(
		TicketNetworkEvent event,
		TicketNetworkListing ticketGroup,
		Status status
	) {
		return new TicketNetworkCachedListing(new TicketNetworkCachedEvent(event), ticketGroup, status);
	}

	@Override
	public void check() {
		check(CheckOptions.defaults());
	}

	@Override
	public void check(CheckOptions options) {
		doCheck(options);
	}

	private class CheckContext {

		private final CheckOptions options;

		/**
		 * Ticket group ID to ticket group info mapping.
		 */
		private final Map<Integer, TicketGroupInfo> ticketGroupInfoMapping;

		/**
		 * The ticket group IDs listed on the marketplace.
		 */
		private final Set<Integer> listedTicketGroupIds = ConcurrentHashMap.newKeySet();

		/**
		 * The checking tasks.
		 */
		private final List<CompletableFuture<TicketGroupsV4GetModel>> checkings;

		/**
		 * The tasks to delete or update the listings.
		 */
		private final List<CompletableFuture<Void>> tasks;

		private final AtomicInteger skip;

		public CheckContext(CheckOptions options, Map<Integer, TicketGroupInfo> ticketGroupInfoMapping) {
			this.options = options;
			this.ticketGroupInfoMapping = ticketGroupInfoMapping;
			this.checkings = Collections.synchronizedList(new ArrayList<>());
			this.tasks = Collections.synchronizedList(new ArrayList<>());
			this.skip = new AtomicInteger();
		}

		public Map<Integer, TicketGroupInfo> getTicketGroupInfoMapping() {
			return ticketGroupInfoMapping;
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
		public Set<TicketGroupInfo> getMissingTicketGroupInfos() {
			// missing = cached - listed
			var missingTicketGroupInfos = new HashSet<>(ticketGroupInfoMapping.values());
			missingTicketGroupInfos.removeIf(t -> listedTicketGroupIds.contains(t.getTicketGroupId()));
			log.debug("missing ticket group info count: {}", missingTicketGroupInfos::size);
			return missingTicketGroupInfos;
		}

	}

	private class TicketGroupInfo {

		private final String cacheName;

		/**
		 * @see TicketNetworkCachedListing#getId()
		 */
		private final String listingId;

		@Nullable
		private final Integer ticketGroupId;

		public TicketGroupInfo(String cacheName, TicketNetworkCachedListing c) {
			this.cacheName = cacheName;
			this.listingId = c.getId();
			this.ticketGroupId = c.getTicketGroupId();
		}

		public String getCacheName() {
			return cacheName;
		}

		public String getListingId() {
			return listingId;
		}

		public Integer getTicketGroupId() {
			return ticketGroupId;
		}

	}

	private void doCheck(CheckOptions options) {
		log.info("[check] begin.");

		// Create a stop watch to measure the time taken to check the listings.
		StopWatch stopWatch = StopWatch.createStarted();

		CheckContext ctx = newCheckContext(options);

		// Check the first page
		TicketGroupsV4GetModel firstPage = check(ctx, ctx.nextPage()).join();

		// Check the next page to the last page
		log.debug("[check] total items: {}.", firstPage::getTotalCount);

		int pageCount = (firstPage.getTotalCount() + options.pageSize() - 1) / options.pageSize();

		for (int i = 1; i < pageCount; i++) {
			ctx.addChecking(check(ctx, ctx.nextPage()));
		}

		// Wait all checking tasks to complete
		log.debug("[check] waiting for all checking tasks to complete, checking size: {}", ctx::checkingCount);
		ctx.joinCheckings();

		// Wait all tasks to complete
		log.debug("[check] waiting for all tasks to complete, task size: {}", ctx::taskCount);
		ctx.joinTasks();

		// Create the listings which in cache but not on the marketplace.
		ctx.getMissingTicketGroupInfos().forEach(t -> {
			var cacheName = t.getCacheName();
			var cache = this.getCache(cacheName);
			var marketplaceCachedListing = cache.get(t.getListingId());

			if (marketplaceCachedListing != null) {
				// Double check if the cached listing still exists.
				var viagogoEvent = marketplaceCachedListing.getEvent().toMarketplaceEvent();
				var viagogoListing = marketplaceCachedListing.toMarketplaceListing();
				try {
					this.createListing(viagogoEvent, viagogoListing);
				} catch (IOException e) {
					log.warn("Create listing failed, external ID: {}.", viagogoListing.getId(), e);
				}
			}
		});

		// Log the time taken to check the listings.
		stopWatch.stop();
		log.info("[check] end, checked {} items in {}", firstPage::getTotalCount, () -> stopWatch);
	}

	/**
	 * Creates a new check context.
	 *
	 * @param options the check options.
	 * @return a new check context.
	 */
	private CheckContext newCheckContext(CheckOptions options) {
		// The mapping of ticket group IDs to their corresponding ticket group informations.
		var ticketGroupInfoMapping = this.getTicketGroupInfoMapping(options);

		// The context for checking.
		return new CheckContext(options, ticketGroupInfoMapping);
	}

	/**
	 * Retrieves a mapping of ticket group ID to their corresponding ticket group info.
	 *
	 * This method iterates over all available cache names, retrieves each cache, 
	 * and then creates a map entry for each ticket group ID pointing to its ticket group info.
	 *
	 * @param options the check options.
	 * @return a map where the keys are ticket group IDs and the values are ticket group informations.
	 */
	private Map<Integer, TicketGroupInfo> getTicketGroupInfoMapping(CheckOptions options) {
		// Create a stop watch to measure the time taken to retrieve the ticket group ID to ticket group informations
		StopWatch stopWatch = StopWatch.createStarted();

		// Create a map to hold the ticket group ID to ticket group info mapping
		Map<Integer, TicketGroupInfo> ticketGroupInfoMapping =
			getCacheNamesStream(options.chunkSize()) // Stream of cache names
				.flatMap(cacheName ->
					// Retrieve the cache and create a stream of ticketGroupId-to-ticketGroupInfo entries
					getCache(cacheName)
						.values()
						.stream()
						.filter(Objects::nonNull)
						.filter(c -> c.getTicketGroupId() != null)
						.map(c -> Map.entry(c.getTicketGroupId(), new TicketGroupInfo(cacheName, c)))
				)
				// Collect the entries into a map
				.collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

		// Log the time taken to retrieve the ticket group ID to cache name mapping
		stopWatch.stop();
		log.debug("[getTicketGroupIdToCacheName] end. Retrieved {} ticket group IDs in {}", ticketGroupInfoMapping::size, () -> stopWatch);

		// Return the map of ticket group IDs to ticket group info
		return ticketGroupInfoMapping;
	}

	private CompletableFuture<TicketGroupsV4GetModel> check(CheckContext ctx, TicketGroupQuery q) {
		return callAsync(() -> {
			TicketGroupsV4GetModel p = inventoryService.getTicketGroups(q);
			Optional.ofNullable(p).ifPresent(t -> check(ctx, t));
			return p;
		}, this.executor);
	}

	/**
	 * Checks the listings in the page.
	 *
	 * @param ctx the context.
	 * @param page the page.
	 */
	private void check(CheckContext ctx, TicketGroupsV4GetModel page) {
		// Delete the listings not in the page
		var deleteTasks = page.getResults().stream()
			.filter(listing -> !ctx.getTicketGroupInfoMapping().containsKey(listing.getTicketGroupId()))
			.map(listing -> this.<Void>callAsync(() -> {
				this.inventoryService.deleteTicketGroup(listing.getTicketGroupId());
				return null;
			})).collect(Collectors.toUnmodifiableList());
		ctx.addTasks(deleteTasks);

		// Check the listings in the page.
		page.getResults().stream()
			.filter(listing -> ctx.getTicketGroupInfoMapping().containsKey(listing.getTicketGroupId()))
			.forEach((TicketGroup listing) -> check(ctx, listing));
	}

	/**
	 * Checks the listing.
	 *
	 * If the listing is not cached, delete the listing from the marketplace.
	 * If the listing is not same as the cached listing, update the listing.
	 *
	 * @param ctx the context.
	 * @param listing the listing.
	 */
	private void check(CheckContext ctx, TicketGroup listing) {
		log.trace("Checking {}", listing::getTicketGroupId);

		ctx.addListedTicketGroupId(listing.getTicketGroupId());

		TicketGroupInfo ticketGroupInfo = ctx.getTicketGroupInfoMapping().get(listing.getTicketGroupId());
		TicketNetworkCachedListing cachedListing = this.getCache(ticketGroupInfo.getCacheName()).get(ticketGroupInfo.getListingId());

		if (cachedListing == null) {
			// Double check the listing if it is not cached.
			// If the listing is not cached, delete the listing from the marketplace.
			ctx.addTask(this.<Void>callAsync(() -> {
				log.trace("Deleting {}", listing::getTicketGroupId);
				this.inventoryService.deleteTicketGroup(listing.getTicketGroupId());
				return null;
			}));
		} else if (!isSame(listing, cachedListing.getRequest())) {
			// If the listing is not same as the cached listing, update the listing.
			ctx.addTask(this.<Void>callAsync(() -> {
				log.trace("Updating {}", listing::getTicketGroupId);

				var e = cachedListing.getEvent().toMarketplaceEvent();
				var l = cachedListing.toMarketplaceListing();
				var p = getPriority(e, l, cachedListing);

				if (e.getMarketplaceEventId().equals(listing.getEvent().getId())) {
					this.updateListing(e, l, cachedListing, p);
				} else {
					log.warn("Marketplace event ID mismatch:  {} != {}, event ID = {}",
						e::getMarketplaceEventId, () -> listing.getEvent().getId(), e::getId);
					this.deleteListing(e, ticketGroupInfo.getListingId(), cachedListing, p);
				}
				return null;
			}));
		}
	}

	private boolean isSame(TicketGroup listing, TicketGroupV4PostModel request) {
		boolean equals = Listings.isSame(listing, request);
		log.debug("[isSame] listing: {}, request: {}, equals: {}",
			() -> Listings.toString(listing), () -> Listings.toString(request), () -> equals);
		return equals;
	}

}
