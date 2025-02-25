package org.oxerr.ticketnetwork.client.cached.redisson;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

import org.oxerr.ticketnetwork.client.TicketNetworkClient;
import org.oxerr.ticketnetwork.client.cached.CachedTicketNetworkClient;
import org.oxerr.ticketnetwork.client.cached.redisson.inventory.RedissonCachedTicketGroupService;
import org.redisson.api.RedissonClient;

public class RedissonCachedTicketNetworkClient implements CachedTicketNetworkClient {

	private final TicketNetworkClient ticketNetworkClient;

	private final RedissonCachedTicketGroupService cachedSellerListingService;

	public RedissonCachedTicketNetworkClient(
		TicketNetworkClient ticketNetworkClient,
		RedissonClient redissonClient,
		String keyPrefix
	) {
		this(ticketNetworkClient, redissonClient, keyPrefix, ForkJoinPool.commonPool());
	}

	public RedissonCachedTicketNetworkClient(
		TicketNetworkClient ticketNetworkClient,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor
	) {
		this(
			ticketNetworkClient,
			new RedissonCachedTicketGroupService(
				ticketNetworkClient.getInventoryService(),
				redissonClient,
				keyPrefix,
				executor
			)
		);
	}

	public RedissonCachedTicketNetworkClient(
		TicketNetworkClient ticketNetworkClient,
		RedissonCachedTicketGroupService cachedSellerListingService
	) {
		this.ticketNetworkClient = ticketNetworkClient;
		this.cachedSellerListingService = cachedSellerListingService;
	}

	@Override
	public TicketNetworkClient getClient() {
		return ticketNetworkClient;
	}

	@Override
	public RedissonCachedTicketGroupService getCachedListingService() {
		return cachedSellerListingService;
	}

}
