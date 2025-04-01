package org.oxerr.ticketnetwork.client.rescu.impl;

import org.oxerr.rescu.ext.singleton.RestProxyFactorySingletonImpl;
import org.oxerr.ticketnetwork.client.TicketNetworkClient;
import org.oxerr.ticketnetwork.client.inventory.InventoryService;
import org.oxerr.ticketnetwork.client.rescu.impl.inventory.InventoryServiceImpl;
import org.oxerr.ticketnetwork.client.rescu.resource.inventory.InventoryResource;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.ws.rs.HeaderParam;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.Interceptor;
import si.mazi.rescu.RestProxyFactoryImpl;
import si.mazi.rescu.clients.HttpConnectionType;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonObjectMapperFactory;

public class ResCUTicketNetworkClient implements TicketNetworkClient {

	private static final String DEFAULT_BASE_URL = "https://www.tn-apis.com";

	private final String baseUrl;

	private final IRestProxyFactory restProxyFactory;

	private final InventoryService inventoryService;

	public ResCUTicketNetworkClient(
		Integer brokerId,
		CharSequence accessToken,
		Interceptor... interceptors
	) {
		this(DEFAULT_BASE_URL, brokerId, accessToken, interceptors);
	}

	public ResCUTicketNetworkClient(
		String baseUrl,
		Integer brokerId,
		CharSequence accessToken,
		Interceptor... interceptors
	) {
		this.baseUrl = baseUrl;
		this.restProxyFactory = new RestProxyFactorySingletonImpl(new RestProxyFactoryImpl());

		var clientConfig = createClientConfig(accessToken, brokerId);
		var objectMapper = clientConfig.getJacksonObjectMapperFactory().createObjectMapper();
		InventoryResource inventoryResource = restProxyFactory.createProxy(InventoryResource.class, baseUrl, clientConfig, interceptors);
		this.inventoryService = new InventoryServiceImpl(objectMapper, inventoryResource);
	}

	@Override
	public InventoryService getInventoryService() {
		return inventoryService;
	}

	protected <I> I createProxy(Class<I> restInterface, ClientConfig clientConfig, Interceptor... interceptors) {
		return this.restProxyFactory.createProxy(restInterface, baseUrl, clientConfig, interceptors);
	}

	protected ClientConfig createClientConfig(CharSequence accessToken, Integer brokerId) {
		var jacksonObjectMapperFactory = createJacksonObjectMapperFactory();
		var clientConfig = new ClientConfig();
		clientConfig.setConnectionType(HttpConnectionType.apache);
		clientConfig.addDefaultParam(HeaderParam.class, "Authorization", "Bearer " + accessToken);
		clientConfig.addDefaultParam(HeaderParam.class, "X-Identity-Context", "broker-id=" + brokerId);
		clientConfig.setJacksonObjectMapperFactory(jacksonObjectMapperFactory);
		return clientConfig;
	}

	protected JacksonObjectMapperFactory createJacksonObjectMapperFactory() {
		return new DefaultJacksonObjectMapperFactory() {

			@Override
			public void configureObjectMapper(ObjectMapper objectMapper) {
				super.configureObjectMapper(objectMapper);
				objectMapper.registerModule(new JavaTimeModule());
				objectMapper.setSerializationInclusion(Include.NON_ABSENT);
				objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
				objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
				objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
			}

		};
	}

}
