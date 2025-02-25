package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.oxerr.ticket.inventory.support.cached.redisson.ListingConfiguration;
import org.oxerr.ticket.inventory.support.cached.redisson.RedissonCachedListingServiceSupport;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;
import org.oxerr.ticketnetwork.client.cached.inventory.TicketNetworkCachedTicketGroupService;
import org.oxerr.ticketnetwork.client.cached.inventory.TicketNetworkEvent;
import org.oxerr.ticketnetwork.client.cached.inventory.TicketNetworkTicketGroup;
import org.oxerr.ticketnetwork.client.inventory.InventoryService;
import org.oxerr.ticketnetwork.client.model.TicketGroupV4PostModel;
import org.redisson.api.RedissonClient;

public class RedissonCachedTicketGroupService
	extends RedissonCachedListingServiceSupport<String, Integer, TicketGroupV4PostModel, TicketNetworkTicketGroup, TicketNetworkEvent, TicketNetworkCachedTicketGroup>
	implements TicketNetworkCachedTicketGroupService {

	private final InventoryService inventoryService;

	/**
	 * Constructs with default {@link ListingConfiguration} and default {@link RetryConfiguration}.
	 *
	 * @param inventoryService the inventory service.
	 * @param redissonClient the redisson client.
	 * @param keyPrefix the key prefix for the cache.
	 * @param executor the executor.
	 */
	public RedissonCachedTicketGroupService(
		InventoryService inventoryService,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor
	) {
		this(inventoryService, redissonClient, keyPrefix, executor, new ListingConfiguration());
	}

	/**
	 * Constructs with specified {@link ListingConfiguration} and specified {@link RetryConfiguration}.
	 *
	 * @param inventoryService the inventory service.
	 * @param redissonClient the redisson client.
	 * @param keyPrefix the key prefix for the cache.
	 * @param executor the executor.
	 * @param listingConfiguration the listing configuration.
	 */
	public RedissonCachedTicketGroupService(
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
		@Nonnull TicketNetworkTicketGroup ticketGroup,
		@Nullable TicketNetworkCachedTicketGroup cachedListing
	) {
		boolean shouldCreate = super.shouldCreate(event, ticketGroup, cachedListing);
		return shouldCreate || (cachedListing != null && !Objects.equals(ticketGroup.getTicketNetworkEventId(), cachedListing.getTicketNetworkEventId()));
	}

	@Override
	protected boolean shouldUpdate(
		@Nonnull TicketNetworkEvent event,
		@Nonnull TicketNetworkTicketGroup ticketGroup,
		@Nullable TicketNetworkCachedTicketGroup cachedListing
	) {
		boolean shouldUpdate = super.shouldUpdate(event, ticketGroup, cachedListing);
		return shouldUpdate || (cachedListing != null && !Objects.equals(ticketGroup.getTicketNetworkEventId(), cachedListing.getTicketNetworkEventId()));
	}

	@Override
	protected int getPriority(
		@Nonnull TicketNetworkEvent event,
		@Nullable TicketNetworkTicketGroup ticketGroup,
		@Nullable TicketNetworkCachedTicketGroup cachedListing
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
		@Nonnull Set<Integer> inventoryListingIds,
		@Nonnull Integer ticketGroupId,
		@Nonnull TicketNetworkCachedTicketGroup cachedListing
	) {
		return super.shouldDelete(event, inventoryListingIds, ticketGroupId, cachedListing)
			|| !Objects.equals(event.getTicketNetworkEventId(), cachedListing.getTicketNetworkEventId());
	}

	@Override
	protected void createListing(TicketNetworkEvent event, TicketNetworkTicketGroup ticketGroup) throws IOException {
		this.inventoryService.createTicketGroup(ticketGroup.getRequest());
	}

	@Override
	protected void deleteListing(TicketNetworkEvent event, Integer ticketGroupId) throws IOException {
		this.inventoryService.deleteTicketGroup(ticketGroupId);
	}

	@Override
	protected TicketNetworkCachedTicketGroup toCached(TicketNetworkEvent event, TicketNetworkTicketGroup ticketGroup, Status status) {
		return new TicketNetworkCachedTicketGroup(new TicketNetworkCachedEvent(event), ticketGroup, status);
	}

}
