package org.oxerr.ticketnetwork.client.rescu.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResCUTicketNetworkClients {

	public static ResCUTicketNetworkClient createClient() {
		Properties props = getProps();
		String baseUrl = props.getProperty("base_url");
		String consumerKey = props.getProperty("consumer.key");
		String consumerSecret = props.getProperty("consumer.secret");
		String accessToken = props.getProperty("access_token");
		String brokerId = props.getProperty("broker_id");
		return new ResCUTicketNetworkClient(baseUrl, consumerKey, consumerSecret, accessToken, brokerId);
	}

	private static Properties getProps() {
		Properties props = new Properties();
		String name = "/ticketnetwork.properties";
		try (InputStream in = ResCUTicketNetworkClients.class.getResourceAsStream(name)) {
			if (in != null) {
				props.load(in);
			} else {
				throw new IllegalArgumentException(String.format("No resource found: %s", name));
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("Read " + name + " failed.");
		}
		return props;
	}

}
