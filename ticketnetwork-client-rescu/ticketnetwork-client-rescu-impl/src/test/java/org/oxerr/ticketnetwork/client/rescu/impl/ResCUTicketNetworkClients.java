package org.oxerr.ticketnetwork.client.rescu.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResCUTicketNetworkClients {

	public static ResCUTicketNetworkClient getClient() {
		Properties props = getProps();
		String baseUrl = props.getProperty("baseUrl");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		return new ResCUTicketNetworkClient(baseUrl, username, password);
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
