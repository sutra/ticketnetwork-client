package org.oxerr.ticketnetwork.client.rescu.impl;

import org.oxerr.rescu.ext.singleton.RestProxyFactorySingletonImpl;
import org.oxerr.ticketnetwork.client.TicketNetworkClient;
import org.oxerr.ticketnetwork.client.inventory.InventoryService;
import org.oxerr.ticketnetwork.client.rescu.impl.inventory.InventoryServiceImpl;
import org.oxerr.ticketnetwork.client.rescu.resource.InventoryResource;

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
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonObjectMapperFactory;

public class ResCUTicketNetworkClient implements TicketNetworkClient {

	private static final String DEFAULT_BASE_URL = "https://www.tn-apis.com";

	private final String baseUrl;

	private final IRestProxyFactory restProxyFactory;

	private final InventoryService inventoryService;

	public ResCUTicketNetworkClient(String username, String password, Interceptor...interceptors) {
		this(DEFAULT_BASE_URL, username, password, interceptors);
	}

	public ResCUTicketNetworkClient(String baseUrl, String username, String password, Interceptor... interceptors) {
		this.baseUrl = baseUrl;
		JacksonObjectMapperFactory jacksonObjectMapperFactory = createJacksonObjectMapperFactory();
		var clientConfig = createClientConfig(jacksonObjectMapperFactory, username, password);
		this.restProxyFactory = new RestProxyFactorySingletonImpl(new RestProxyFactoryImpl());
		this.inventoryService = new InventoryServiceImpl(
			this.restProxyFactory.createProxy(InventoryResource.class, baseUrl, clientConfig, interceptors)
		);
	}

	@Override
	public InventoryService getInventoryService() {
		return this.inventoryService;
	}

	protected <I> I createProxy(Class<I> restInterface, ClientConfig clientConfig, Interceptor... interceptors) {
		return this.restProxyFactory.createProxy(restInterface, baseUrl, clientConfig, interceptors);
	}

	protected ClientConfig createClientConfig(JacksonObjectMapperFactory jacksonObjectMapperFactory, String username, String password) {
		var clientConfig = new ClientConfig();
		clientConfig.addDefaultParam(HeaderParam.class, "Authorization", username + ":" + password);
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
