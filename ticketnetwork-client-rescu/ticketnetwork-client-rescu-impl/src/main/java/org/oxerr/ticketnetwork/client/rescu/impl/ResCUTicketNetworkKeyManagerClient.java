package org.oxerr.ticketnetwork.client.rescu.impl;

import java.util.Base64;

import org.oxerr.rescu.ext.singleton.RestProxyFactorySingletonImpl;
import org.oxerr.ticketnetwork.client.TicketNetworkKeyManagerClient;
import org.oxerr.ticketnetwork.client.oauth2.TokenService;
import org.oxerr.ticketnetwork.client.rescu.impl.oauth2.TokenServiceImpl;
import org.oxerr.ticketnetwork.client.rescu.resource.oauth2.TokenResource;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;

import jakarta.ws.rs.HeaderParam;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.Interceptor;
import si.mazi.rescu.RestProxyFactoryImpl;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonObjectMapperFactory;

public class ResCUTicketNetworkKeyManagerClient implements TicketNetworkKeyManagerClient {

	private static final String BASE_URL = "https://key-manager.tn-apis.com";

	private final IRestProxyFactory restProxyFactory;

	private final TokenService tokenService;

	public ResCUTicketNetworkKeyManagerClient(
		String consumerKey,
		String consumerSecret,
		Interceptor... interceptors
	) {
		this.restProxyFactory = new RestProxyFactorySingletonImpl(new RestProxyFactoryImpl());

		var clientConfig = createClientConfig(consumerKey, consumerSecret);
		TokenResource tokenResource = restProxyFactory.createProxy(TokenResource.class, BASE_URL, clientConfig, interceptors);
		this.tokenService = new TokenServiceImpl(tokenResource);
	}

	@Override
	public TokenService getTokenService() {
		return tokenService;
	}

	/**
	 * Create a {@link ClientConfig} for Key Manager.
	 *
	 * @param consumerKey The public key of the application, used to uniquely
	 * identify the application in the environment. This value does not need to
	 * be kept secret. Consumer key will be different between Sandbox and
	 * Production for the same application.
	 * @param consumerSecret The private key of the application for the
	 * environment (again, Sandbox and Producation will differ). Used along
	 * with the consumer key to generate access tokens for the application.
	 * Treat this information as you would a password. Consumer secret will be
	 * different between Sandbox and Production for the same application.
	 * @return a {@link ClientConfig} for Key Manager.
	 */
	protected ClientConfig createClientConfig(String consumerKey, String consumerSecret) {
		var jacksonObjectMapperFactory = createJacksonObjectMapperFactory();
		var base64EncodedValue = Base64.getEncoder().encodeToString((consumerKey + ":" + consumerSecret).getBytes());
		var clientConfig = new ClientConfig();
		clientConfig.addDefaultParam(HeaderParam.class, "Authorization", "Basic " + base64EncodedValue);
		clientConfig.setJacksonObjectMapperFactory(jacksonObjectMapperFactory);
		return clientConfig;
	}

	protected JacksonObjectMapperFactory createJacksonObjectMapperFactory() {
		return new DefaultJacksonObjectMapperFactory() {

			@Override
			public void configureObjectMapper(ObjectMapper objectMapper) {
				super.configureObjectMapper(objectMapper);
				objectMapper.setSerializationInclusion(Include.NON_ABSENT);
				objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
				objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
				objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
				objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
			}

		};
	}

}
