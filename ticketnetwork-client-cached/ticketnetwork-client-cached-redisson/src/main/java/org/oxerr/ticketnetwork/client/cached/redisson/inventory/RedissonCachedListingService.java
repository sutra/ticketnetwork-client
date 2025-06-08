package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import java.util.List;
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
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
import org.redisson.api.RMap;
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
	 * @param redisson the redisson client.
	 * @param keyPrefix the key prefix for the cache.
	 * @param executor the executor.
	 */
	public RedissonCachedListingService(
		InventoryService inventoryService,
		RedissonClient redisson,
		String keyPrefix,
		Executor executor
	) {
		this(inventoryService, redisson, keyPrefix, executor, new ListingConfiguration());
	}

	/**
	 * Constructs with specified {@link ListingConfiguration}.
	 *
	 * @param inventoryService the inventory service.
	 * @param redisson the redisson client.
	 * @param keyPrefix the key prefix for the cache.
	 * @param executor the executor.
	 * @param listingConfiguration the listing configuration.
	 */
	public RedissonCachedListingService(
		InventoryService inventoryService,
		RedissonClient redisson,
		String keyPrefix,
		Executor executor,
		ListingConfiguration listingConfiguration
	) {
		super(redisson, keyPrefix, executor, listingConfiguration);
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
	protected void createListing(TicketNetworkEvent event, TicketNetworkListing listing) {
		TicketGroup ticketGroup;

		try {
			log.debug(
				"Creating ticket group. Event ID: {}, reference ticket group ID: {}, low seat: {}.",
				listing.getRequest().getEventId(),
				listing.getRequest().getReferenceTicketGroupId(),
				listing.getRequest().getLowSeat()
			);
			ticketGroup = inventoryService.createTicketGroup(listing.getRequest());
		} catch (ValidationErrorsModel e) {
			log.debug(
				"Create ticket group failed. Event ID: {}, reference ticket group ID: {}, low seat: {}, validation errors: {}.",
				listing.getRequest().getEventId(),
				listing.getRequest().getReferenceTicketGroupId(),
				listing.getRequest().getLowSeat(),
				e.getValidationErrors()
			);
			if (e.getValidationErrors().get("lowSeat") != null
				&& e.getValidationErrors().get("lowSeat").getReasons()
					.contains("A ticket group already exists with the provided event ID, section, and row with overlapping seats.")) {

				TicketGroupsV4GetModel ticketGroups = getTicketGroups(listing);
				log.debug("Ticket group count: {}.", ticketGroups.getTotalCount());

				var existing = ticketGroups.getResults()
					.stream()
					.filter(t -> t.getReferenceTicketGroupId().equals(listing.getRequest().getReferenceTicketGroupId()))
					.findFirst();

				log.debug("Existing ticket group: {}", existing);

				if (existing.isPresent()) {
					ticketGroup = existing.get();
					log.debug("Found existing ticket group: {}", ticketGroup::getTicketGroupId);
				} else {
					for (var t : ticketGroups.getResults()) {
						this.inventoryService.deleteTicketGroup(t.getTicketGroupId());
					}
					ticketGroup = inventoryService.createTicketGroup(listing.getRequest());
				}
			} else {
				throw e;
			}
		}

		// Update ticket group ID in cache
		updateCache(event, listing, ticketGroup);
	}

	@Override
	public void updateCache(String eventId, String listingId, Integer ticketGroupId) {
		TicketNetworkCachedListing cached = getEventCache(eventId).get(listingId);
		cached.setTicketGroupId(ticketGroupId);

		getEventCache(eventId).put(listingId, cached);
	}

	private TicketGroupsV4GetModel getTicketGroups(TicketNetworkListing listing) {
		var filter = String.format(
			"event/id eq %d and seats/section eq '%s' and seats/row eq '%s'",
			listing.getRequest().getEventId(),
			listing.getRequest().getSection().replace("'", "''"),
			listing.getRequest().getRow().replace("'", "''")
		);

		TicketGroupQuery q = new TicketGroupQuery();
		q.setFilter(filter);

		TicketGroupsV4GetModel ticketGroups = inventoryService.getTicketGroups(q);

		log.debug("Filter: {}, ticket group count: {}.", filter, ticketGroups.getTotalCount());

		if (log.isDebugEnabled()) {
			ticketGroups.getResults()
				.forEach(t -> log.debug(
					"Ticket group. Event ID: {} reference ticket group ID: {}, low seat: {}.",
					t.getEvent().getId(),
					t.getReferenceTicketGroupId(),
					t.getSeats().getLowSeat()
				));
		}

		return ticketGroups;
	}

	@Override
	protected void updateListing(
		TicketNetworkEvent event,
		TicketNetworkListing target,
		TicketNetworkListing source,
		int priority
	) {
		log.debug("Updating listing. source: {}, target: {}", source, target);
		inventoryService.updateTicketGroup(source.getTicketGroupId(), target.getRequest(), source.getRequest());
	}

	@Override
	protected void deleteListing(
		TicketNetworkEvent event,
		String listingId,
		TicketNetworkCachedListing cachedListing,
		int priority
	) {
		if (cachedListing.getTicketGroupId() != null) {
			deleteListing(cachedListing.getTicketGroupId());
		}
	}

	protected void deleteListing(Integer ticketGroupId) {
		inventoryService.deleteTicketGroup(ticketGroupId);
	}

	@Override
	protected void deleteListing(TicketNetworkEvent event, String listingId) {
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
		log.info("[check] begin.");

		// Create a stop watch to measure the time taken to check the listings.
		StopWatch stopWatch = StopWatch.createStarted();

		CheckContext ctx = newCheckContext(options);

		// Check the first page
		TicketGroupsV4GetModel firstPage = checkPage(ctx, ctx.nextPage()).join();

		// Check the next page to the last page
		log.info("[check] total items: {}.", firstPage::getTotalCount);

		int pageCount = (firstPage.getTotalCount() + options.pageSize() - 1) / options.pageSize();

		log.info("[check] page count: {}.", () -> pageCount);

		for (int i = 1; i < pageCount; i++) {
			log.debug("[check] checking page: {}/{}", i, pageCount);
			ctx.addChecking(checkPage(ctx, ctx.nextPage()));
		}

		// Wait all checking tasks to complete
		log.info("[check] waiting for all checking tasks to complete, checking size: {}", ctx::checkingCount);
		ctx.joinCheckings();

		// Wait all tasks to complete
		log.info("[check] waiting for all tasks to complete, task size: {}", ctx::taskCount);
		ctx.joinTasks();

		// Create the listings which in cache but not on the marketplace.
		if (ctx.getOptions().create()) {
			checkCreate(ctx);
		}

		// Log the time taken to check the listings.
		stopWatch.stop();
		log.info("[check] end, checked {} items in {}", firstPage::getTotalCount, () -> stopWatch);
	}

	private CompletableFuture<TicketGroupsV4GetModel> checkPage(CheckContext ctx, TicketGroupQuery q) {
		log.debug("[check] checking page, skip: {}", q::getSkip);
		return callAsync(() -> {
			try {
				TicketGroupsV4GetModel p = inventoryService.getTicketGroups(q);
				Optional.ofNullable(p).ifPresent(t -> checkPage(ctx, t));
				return p;
			} catch (Exception e) {
				log.warn("Check failed, skip: {}, message: {}.", q::getSkip, e::getMessage);
				return null;
			}
		}, this.executor);
	}

	/**
	 * Checks the listings in the page.
	 *
	 * @param ctx the context.
	 * @param page the page.
	 */
	private void checkPage(CheckContext ctx, TicketGroupsV4GetModel page) {
		// Delete the listings not in the cache
		if (ctx.getOptions().delete()) {
			checkDelete(ctx, page);
		}

		// Check the listings in the page.
		page.getResults().stream()
			.filter(listing -> ctx.getCaches().containsKey(new ListingInfo(listing)))
			.forEach((TicketGroup listing) -> checkListing(ctx, listing));
	}

	private void checkDelete(CheckContext ctx, TicketGroupsV4GetModel page) {
		var deleteTasks = page.getResults().stream()
			.filter(listing -> !ctx.getCaches().containsKey(new ListingInfo(listing)))
			.map(listing -> callAsync(() -> delete(listing))).collect(Collectors.toUnmodifiableList());
		ctx.addTasks(deleteTasks);
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
	private void checkListing(CheckContext ctx, TicketGroup listing) {
		log.trace("Checking {}", listing::getTicketGroupId);

		ctx.addListedTicketGroupId(listing.getTicketGroupId());

		CacheInfo ticketGroupInfo = ctx.getCaches().get(new ListingInfo(listing));
		TicketNetworkCachedListing cachedListing = this.getCache(ticketGroupInfo.getCacheName()).get(ticketGroupInfo.getListingId());

		if (cachedListing == null) {
			// Double check the listing if it is not cached.
			// If the listing is not cached, delete the listing from the marketplace.

			if (ctx.getOptions().delete()) {
				checkDelete(ctx, listing);
			}
		} else if (!isSame(listing, cachedListing.getRequest())) {
			// If the listing on marketplace is not same as the cached listing,
			// update the listing.

			if (ctx.getOptions().update()) {
				checkUpdate(ctx, listing, ticketGroupInfo, cachedListing);
			}
		}
	}

	private void checkDelete(CheckContext ctx, TicketGroup listing) {
		ctx.addTask(callAsync(() -> delete(listing)));
	}

	private void checkUpdate(CheckContext ctx, TicketGroup listing, CacheInfo ticketGroupInfo,
			TicketNetworkCachedListing cachedListing) {
		ctx.addTask(callAsync(() -> update(listing, ticketGroupInfo, cachedListing)));
	}

	private Void update(TicketGroup listing, CacheInfo ticketGroupInfo, TicketNetworkCachedListing cachedListing) {
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
					log.debug(
						"Update listing failed, external ID: {}. listing: {}, cachedListing: {}, error: {}.",
						target.getTicketGroupId(),
						ToStringBuilder.reflectionToString(listing, ToStringStyle.JSON_STYLE),
						ToStringBuilder.reflectionToString(cachedListing.getRequest(), ToStringStyle.JSON_STYLE),
						e.toString(),
						e
					);
					boolean isTicketGroupAlreadyExists = e.getValidationErrors() != null
						&& e.getValidationErrors().get("ticketGroupId") != null
						&& e.getValidationErrors().get("ticketGroupId").getReasons()
							.contains("Ticket group already exists.");
					if (isTicketGroupAlreadyExists) {
						log.debug("Ticket group already exists, deleting {}.", ticketGroupInfo.getListingId());
						deleteListing(event, ticketGroupInfo.getListingId(), cachedListing, priority);
					}
				}
			}
		} else {
			log.warn("Marketplace event ID mismatch:  {} != {}, event ID = {}",
				event::getMarketplaceEventId, () -> listing.getEvent().getId(), event::getId);
			deleteListing(event, ticketGroupInfo.getListingId(), cachedListing, priority);
		}

		return null;
	}

	private Void delete(TicketGroup listing) {
		log.trace("Deleting {}", listing::getTicketGroupId);
		this.deleteListing(listing.getTicketGroupId());
		return null;
	}

	private void checkCreate(CheckContext ctx) {
		Set<CacheInfo> missing = ctx.getMissingTicketGroupInfos();
		log.info("missing ticket group info count: {}", missing::size);

		List<CompletableFuture<Void>> createTasks = missing.stream()
			.map(cacheInfo -> {
				String cacheName = cacheInfo.getCacheName();
				RMap<String, TicketNetworkCachedListing> cache = this.getCache(cacheName);
				return cache.get(cacheInfo.getListingId());
			})
			// Double check if the cached listing still exists.
			.filter(Objects::nonNull)
			.map(marketplaceCachedListing -> this.<Void>callAsync(() -> {
				TicketNetworkEvent marketplaceEvent = marketplaceCachedListing.getEvent().toMarketplaceEvent();
				TicketNetworkListing marketplaceListing = marketplaceCachedListing.toMarketplaceListing();

				try {
					this.createListing(marketplaceEvent, marketplaceListing);
				} catch (ValidationErrorsModel e) {
					log.warn("Create listing failed, external ID: {}. validation errors: {}.", marketplaceListing.getId(), e.getValidationErrors());
				}

				return null;
			}))
			.collect(Collectors.toUnmodifiableList());

		log.info("[check] creating missing listings, task size: {}", createTasks::size);
		CompletableFuture.allOf(createTasks.toArray(CompletableFuture[]::new)).join();
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
		log.debug("[getCaches] start.");

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

	private boolean isSame(TicketGroup listing, TicketGroupV4PostModel request) {
		boolean equals = Listings.isSame(listing, request);
		log.debug("[isSame] listing: {}, request: {}, equals: {}",
			() -> Listings.toString(listing), () -> Listings.toString(request), () -> equals);
		return equals;
	}

}
