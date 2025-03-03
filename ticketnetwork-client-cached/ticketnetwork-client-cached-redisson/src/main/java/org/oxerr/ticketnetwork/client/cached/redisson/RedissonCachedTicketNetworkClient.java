package org.oxerr.ticketnetwork.client.cached.redisson;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

import org.oxerr.ticketnetwork.client.TicketNetworkClient;
import org.oxerr.ticketnetwork.client.cached.CachedTicketNetworkClient;
import org.oxerr.ticketnetwork.client.cached.redisson.inventory.RedissonCachedListingService;
import org.redisson.api.RedissonClient;

public class RedissonCachedTicketNetworkClient implements CachedTicketNetworkClient {

	private final TicketNetworkClient ticketNetworkClient;

	private final RedissonCachedListingService cachedSellerListingService;

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
			new RedissonCachedListingService(
				ticketNetworkClient.getInventoryService(),
				redissonClient,
				keyPrefix,
				executor
			)
		);
	}

	public RedissonCachedTicketNetworkClient(
		TicketNetworkClient ticketNetworkClient,
		RedissonCachedListingService cachedSellerListingService
	) {
		this.ticketNetworkClient = ticketNetworkClient;
		this.cachedSellerListingService = cachedSellerListingService;
	}

	@Override
	public TicketNetworkClient getClient() {
		return ticketNetworkClient;
	}

	@Override
	public RedissonCachedListingService getCachedListingService() {
		return cachedSellerListingService;
	}

}
