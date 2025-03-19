package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.oxerr.ticket.inventory.support.cached.redisson.ListingConfiguration;
import org.oxerr.ticket.inventory.support.cached.redisson.RedissonCachedListingServiceSupport;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;
import org.oxerr.ticketnetwork.client.cached.inventory.TicketNetworkCachedListingService;
import org.oxerr.ticketnetwork.client.cached.inventory.TicketNetworkEvent;
import org.oxerr.ticketnetwork.client.cached.inventory.TicketNetworkListing;
import org.oxerr.ticketnetwork.client.inventory.InventoryService;
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
		return shouldCreate || (cachedListing != null && !Objects.equals(ticketGroup.getTicketNetworkEventId(), cachedListing.getEvent().getTicketNetworkEventId()));
	}

	@Override
	protected boolean shouldUpdate(
		@Nonnull TicketNetworkEvent event,
		@Nonnull TicketNetworkListing ticketGroup,
		@Nullable TicketNetworkCachedListing cachedListing
	) {
		boolean shouldUpdate = super.shouldUpdate(event, ticketGroup, cachedListing);
		return shouldUpdate || (cachedListing != null && !Objects.equals(ticketGroup.getTicketNetworkEventId(), cachedListing.getEvent().getTicketNetworkEventId()));
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
			|| !Objects.equals(event.getTicketNetworkEventId(), cachedListing.getEvent().getTicketNetworkEventId());
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
					event.getTicketNetworkEventId(), listing.getRequest().getSection(), listing.getRequest().getRow());

				TicketGroupsV4GetModel ticketGroups = inventoryService.getTicketGroups(null, null, null, null, null, null, filter, null);
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

}
