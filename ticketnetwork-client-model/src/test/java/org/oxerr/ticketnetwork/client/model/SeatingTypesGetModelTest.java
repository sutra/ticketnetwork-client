package org.oxerr.ticketnetwork.client.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

class SeatingTypesGetModelTest {

	private static ObjectMapper objectMapper;

	@BeforeAll
	static void setUpBeforeClass() {
		objectMapper = ObjectMappers.createObjectMapper();
	}

	@Test
	void test() throws IOException {
		try (InputStream src = this.getClass().getResourceAsStream("SeatingTypesGetModel.json")) {
			SeatingTypesGetModel seatingTypesGetModel = objectMapper.readValue(src, SeatingTypesGetModel.class);
			assertEquals(3, seatingTypesGetModel.getSeatingTypes().size());

			SeatingType seatingType = seatingTypesGetModel.getSeatingTypes().get(0);
			assertEquals(1, seatingType.getId().intValue());
			assertEquals("Odd/Even", seatingType.getDescription());
		}
	}

}
