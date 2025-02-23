package org.oxerr.ticketnetwork.client.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

class ValidationErrorsModelTest {

	private static ObjectMapper objectMapper;

	@BeforeAll
	static void setUpBeforeClass() {
		objectMapper = new ObjectMapper();
	}

	@Test
	void test() throws IOException {
		try (InputStream src = this.getClass().getResourceAsStream("ValidationErrorsModel.json")) {
			ValidationErrorsModel validationErrorsModel = objectMapper.readValue(src, ValidationErrorsModel.class);
			assertEquals("The request is invalid. Model validation failed.", validationErrorsModel.getMessage());
			assertEquals("The EventId field is required.", validationErrorsModel.getValidationErrors().get("eventId").getReasons().get(0));
			assertEquals("The Quantity field is required.", validationErrorsModel.getValidationErrors().get("quantity").getReasons().get(0));
			assertEquals("The SeatingTypeId field is required.", validationErrorsModel.getValidationErrors().get("seatingTypeId").getReasons().get(0));
			assertEquals("The UnitPrice field is required.", validationErrorsModel.getValidationErrors().get("unitPrice").getReasons().get(0));
		}
	}

}
