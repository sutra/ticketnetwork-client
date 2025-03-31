package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.SerializationUtils;
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

	final Logger log = LogManager.getLogger();

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
			log.info(
				"Creating ticket group. Reference ticket group ID: {}, event ID: {}.",
				listing.getRequest().getReferenceTicketGroupId(),
				listing.getRequest().getEventId()
			);
			ticketGroup = inventoryService.createTicketGroup(listing.getRequest());
		} catch (ValidationErrorsModel e) {
			log.info(
				"Create ticket group failed. Reference ticket group ID: {}, event ID: {}, validation errors: {}.",
				listing.getRequest().getReferenceTicketGroupId(),
				listing.getRequest().getEventId(),
				e.getValidationErrors()
			);
			if (e.getValidationErrors().get("lowSeat") != null
				&& e.getValidationErrors().get("lowSeat").getReasons()
					.contains("A ticket group already exists with the provided event ID, section, and row with overlapping seats.")) {

				var filter = String.format("event/id eq %d and seats/section eq '%s' and seats/row eq '%s'",
					listing.getRequest().getEventId(),
					listing.getRequest().getSection().replace("'", "''"),
					listing.getRequest().getRow().replace("'", "''")
				);

				TicketGroupQuery q = new TicketGroupQuery();
				q.setFilter(filter);
				TicketGroupsV4GetModel ticketGroups = inventoryService.getTicketGroups(q);
				log.info("Filter: {}, ticket group count: {}", filter, ticketGroups.getTotalCount());
				log.debug("Filter: {}, ticket groups: {}", filter, ticketGroups.getResults());

				var existing = ticketGroups.getResults()
					.stream()
					.filter(t -> t.getReferenceTicketGroupId().equals(listing.getRequest().getReferenceTicketGroupId()))
					.findFirst();
				log.debug("Existing ticket group: {}", existing);
				if (existing.isPresent()) {
					ticketGroup = existing.get();
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
		TicketNetworkListing target,
		TicketNetworkListing source,
		int priority
	) throws IOException {
		log.debug("Updating listing. source: {}, target: {}", source, target);
		inventoryService.updateTicketGroup(source.getTicketGroupId(), target.getRequest(), source.getRequest());
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
		var missing = ctx.getMissingTicketGroupInfos();
		log.debug("missing ticket group info count: {}", missing::size);
		missing.forEach(t -> {
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
		var caches = this.getCaches(options);
		return new CheckContext(options, caches);
	}

	/**
	 * Retrieves a mapping of listing info to their corresponding cache info.
	 *
	 * This method iterates over all available cache names, retrieves each cache, 
	 * and then creates a map entry for each listing pointing to its cache info.
	 *
	 * @param options the check options.
	 * @return a map where the keys are listings and the values are cache informations.
	 */
	private Map<ListingInfo, CacheInfo> getCaches(CheckOptions options) {
		// Create a stop watch to measure the time taken to retrieve the caches.
		StopWatch stopWatch = StopWatch.createStarted();

		// Create a map to hold the listings to cache mapping
		Map<ListingInfo, CacheInfo> caches =
			getCacheNamesStream(options.chunkSize()) // Stream of cache names
				.flatMap(cacheName ->
					// Retrieve the cache and create a stream of ticketGroupId-to-ticketGroupInfo entries
					getCache(cacheName)
						.values()
						.stream()
						.map(c -> Map.entry(new ListingInfo(c), new CacheInfo(cacheName, c)))
				)
				// Collect the entries into a map
				.collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

		// Log the time taken to retrieve the listing to cache info mapping.
		stopWatch.stop();
		log.debug("[getCaches] end. Retrieved {} caches in {}", caches::size, () -> stopWatch);

		// Return the map of listings to cache informations.
		return caches;
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
		// Delete the listings not in the cache
		var deleteTasks = page.getResults().stream()
			.filter(listing -> !ctx.getCaches().containsKey(new ListingInfo(listing)))
			.map(listing -> this.<Void>callAsync(() -> {
				this.inventoryService.deleteTicketGroup(listing.getTicketGroupId());
				return null;
			})).collect(Collectors.toUnmodifiableList());
		ctx.addTasks(deleteTasks);

		// Check the listings in the page.
		page.getResults().stream()
			.filter(listing -> ctx.getCaches().containsKey(new ListingInfo(listing)))
			.forEach((TicketGroup listing) -> check(ctx, listing));
	}

	/**
	 * Checks the listing.
	 *
	 * If the listing is not cached, delete the listing from the marketplace.
	 * If the listing is not same as the cached listing, update the listing.
	 *
	 * @param ctx the context.
	 * @param listing the listing on marketplace.
	 */
	private void check(CheckContext ctx, TicketGroup listing) {
		log.trace("Checking {}", listing::getTicketGroupId);

		ctx.addListedTicketGroupId(listing.getTicketGroupId());

		CacheInfo ticketGroupInfo = ctx.getCaches().get(new ListingInfo(listing));
		TicketNetworkCachedListing cachedListing = this.getCache(ticketGroupInfo.getCacheName()).get(ticketGroupInfo.getListingId());

		if (cachedListing == null) {
			// Double check the listing if it is not cached.
			// If the listing is not cached, delete the listing from the marketplace.

			ctx.addTask(callAsync(() -> {
				log.trace("Deleting {}", listing::getTicketGroupId);
				inventoryService.deleteTicketGroup(listing.getTicketGroupId());
				return null;
			}));
		} else if (!isSame(listing, cachedListing.getRequest())) {
			// If the listing on marketplace is not same as the cached listing,
			// update the listing.

			ctx.addTask(callAsync(() -> {
				log.trace("Updating {}", listing::getTicketGroupId);

				var event = cachedListing.getEvent().toMarketplaceEvent();
				var source = new TicketNetworkListing(
					cachedListing.getId(),
					cachedListing.getEvent().getMarketplaceEventId(),
					new TicketGroupV4PostModel(listing),
					listing.getTicketGroupId()
				);

				// Deep clone the source to avoid modifying the source.
				var target = SerializationUtils.clone(source);
				Listings.patch(target.getRequest(), cachedListing.getRequest());

				var priority = getPriority(event, target, cachedListing);

				if (event.getMarketplaceEventId().equals(listing.getEvent().getId())) {
					if (target.getTicketGroupId() == null) {
						createListing(event, target);
					} else {
						try {
							updateListing(event, target, source, priority);
						} catch (ValidationErrorsModel e) {
							log.warn("Update listing failed, external ID: {}.", target.getTicketGroupId(), e);
						}
					}
				} else {
					log.warn("Marketplace event ID mismatch:  {} != {}, event ID = {}",
						event::getMarketplaceEventId, () -> listing.getEvent().getId(), event::getId);
					deleteListing(event, ticketGroupInfo.getListingId(), cachedListing, priority);
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
