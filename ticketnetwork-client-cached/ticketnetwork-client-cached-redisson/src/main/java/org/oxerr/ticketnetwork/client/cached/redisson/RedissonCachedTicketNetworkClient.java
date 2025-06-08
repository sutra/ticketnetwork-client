package org.oxerr.ticketnetwork.client.cached.redisson;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
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
		RedissonClient redisson,
		String keyPrefix,
		Executor executor
	) {
		this(
			ticketNetworkClient,
			new RedissonCachedListingService(
				ticketNetworkClient.getInventoryService(),
				redisson,
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

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof RedissonCachedTicketNetworkClient)) {
			return false;
		}
		RedissonCachedTicketNetworkClient rhs = (RedissonCachedTicketNetworkClient) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
