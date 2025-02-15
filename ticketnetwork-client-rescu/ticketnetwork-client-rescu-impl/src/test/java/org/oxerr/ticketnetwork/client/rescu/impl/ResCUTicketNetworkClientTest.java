package org.oxerr.ticketnetwork.client.rescu.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

// @Disabled("Token is required")
class ResCUTicketNetworkClientTest {

	public static ResCUTicketNetworkClient getClient() {
		Properties props = getProps();
		String baseUrl = props.getProperty("baseUrl");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		return new ResCUTicketNetworkClient(baseUrl, username, password);
	}

	public static Properties getProps() {
		Properties props = new Properties();
		String name = "/ticketnetwork.properties";
		try (InputStream in = ResCUTicketNetworkClientTest.class.getResourceAsStream(name)) {
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

	@Test
	void testGetClient() {
		assertNotNull(ResCUTicketNetworkClientTest.getClient());
	}

	@Test
	void testGetEvents() {
		ResCUTicketNetworkClient client = getClient();
		client.getInventoryService().getTicketGroups();
	}

}
