package com.demo.route.price.calculator.model;

import com.demo.route.price.calculator.model.request.PriceCalculationRequest;
import com.demo.route.price.calculator.model.response.TicketBundleResponse;
import com.demo.route.price.calculator.util.AbstractTest;
import com.demo.route.price.calculator.util.ResourceHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeserializationTest extends AbstractTest {

    @Test
    void deserializeTicketBundle() throws JsonProcessingException {
        // set-up
        String input = ResourceHelper.readFileFromResources("mapper/input/valid-ticket-bundle.json");

        // execute
        TicketBundleResponse result = objectMapper.readValue(input, TicketBundleResponse.class);

        // validate
        final Passenger passenger = getPassenger();
        final Price price = getPrice();
        final Ticket ticket = getTicket(passenger, price);
        final TicketBundleResponse ticketBundleResponse = getTicketBundle(ticket);

        assertEquals(ticketBundleResponse, result);
    }

    @Test
    void deserializeTicketBundleWithUnknownExtraFields() throws JsonProcessingException {
        // set-up
        String input = ResourceHelper.readFileFromResources("mapper/input/extra-fields-ticket-bundle.json");

        // execute
        TicketBundleResponse result = objectMapper.readValue(input, TicketBundleResponse.class);

        // validate
        final Passenger passenger = getPassenger();
        final Price price = getPrice();
        final Ticket ticket = getTicket(passenger, price);
        final TicketBundleResponse ticketBundleResponse = getTicketBundle(ticket);

        assertEquals(ticketBundleResponse, result);
    }

    @Test
    void deserializePriceCalculationRequest() throws JsonProcessingException {
        // set-up
        String input = ResourceHelper.readFileFromResources("mapper/input/valid-price-calculation-request.json");

        // execute
        PriceCalculationRequest result = objectMapper.readValue(input, PriceCalculationRequest.class);

        // validate
        final Passenger passenger = getPassenger();
        final PriceCalculationRequest priceCalculationRequest = getPriceCalculationRequest(passenger);

        assertEquals(priceCalculationRequest, result);
    }

    @Test
    void deserializePriceCalculationRequestWithUnknownExtraFields() throws JsonProcessingException {
        // set-up
        String input = ResourceHelper.readFileFromResources("mapper/input/extra-fields-price-calculation-request.json");

        // execute
        PriceCalculationRequest result = objectMapper.readValue(input, PriceCalculationRequest.class);

        // validate
        final Passenger passenger = getPassenger();
        final PriceCalculationRequest priceCalculationRequest = getPriceCalculationRequest(passenger);

        assertEquals(priceCalculationRequest, result);
    }
}
