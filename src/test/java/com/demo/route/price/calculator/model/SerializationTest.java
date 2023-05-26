package com.demo.route.price.calculator.model;

import com.demo.route.price.calculator.model.request.PriceCalculationRequest;
import com.demo.route.price.calculator.model.response.TicketBundleResponse;
import com.demo.route.price.calculator.util.AbstractTest;
import com.demo.route.price.calculator.util.ResourceHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

class SerializationTest extends AbstractTest {

    @Test
    void serializeTicketBundle() throws JsonProcessingException, JSONException {
        // set-up
        final Passenger passenger = getPassenger();
        final Price price = getPrice();
        final Ticket ticket = getTicket(passenger, price);
        final TicketBundleResponse ticketBundleResponse = getTicketBundle(ticket);

        // execute
        String result = objectMapper.writeValueAsString(ticketBundleResponse);

        // validate
        String expected = ResourceHelper.readFileFromResources("mapper/serialized/expected/ticket-bundle.json");
        JSONAssert.assertEquals(expected, result, true);
    }

    @Test
    void serializePriseCalculationRequest() throws JsonProcessingException, JSONException {
        // set-up
        final Passenger passenger = getPassenger();
        final PriceCalculationRequest priceCalculationRequest = getPriceCalculationRequest(passenger);

        // execute
        String result = objectMapper.writeValueAsString(priceCalculationRequest);

        // validate
        String expected = ResourceHelper.readFileFromResources("mapper/serialized/expected/price-calculation-request.json");
        JSONAssert.assertEquals(expected, result, true);
    }
}
