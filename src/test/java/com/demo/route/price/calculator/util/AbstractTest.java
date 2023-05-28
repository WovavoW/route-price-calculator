package com.demo.route.price.calculator.util;

import com.demo.route.price.calculator.config.ObjectMapperConfig;
import com.demo.route.price.calculator.model.Passenger;
import com.demo.route.price.calculator.model.Price;
import com.demo.route.price.calculator.model.Ticket;
import com.demo.route.price.calculator.model.enumerated.AgeGroup;
import com.demo.route.price.calculator.model.request.PriceCalculationRequest;
import com.demo.route.price.calculator.model.response.TicketBundleResponse;
import com.demo.route.price.calculator.service.data.CalculatedPrices;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public abstract class AbstractTest {
    protected static final String ROUTE_NAME = "route-name";
    protected static final BigDecimal TAX_RATE = BigDecimal.valueOf(21);
    protected static final BigDecimal BASE_PRICE = BigDecimal.valueOf(10);

    protected final ObjectMapper objectMapper = new ObjectMapperConfig().objectMapper();

    protected BigDecimal getFormattedBigDecimal(double input) {
        return BigDecimal.valueOf(input).setScale(2, RoundingMode.HALF_UP);
    }

    protected TicketBundleResponse getTicketBundle() {
        return getTicketBundle(getTicket());
    }

    protected TicketBundleResponse getTicketBundle(Ticket ticket) {
        return new TicketBundleResponse.Builder()
                .routeName(ROUTE_NAME)
                .totalPrice(getFormattedBigDecimal(39.72))
                .tickets(List.of(ticket))
                .build();
    }

    protected Ticket getTicket() {
        return getTicket(getPassenger(), getPrice());
    }

    protected Ticket getTicket(Passenger passenger, Price price) {
        return new Ticket.Builder()
                .price(price)
                .passenger(passenger)
                .build();
    }

    protected Price getPrice() {
        return getPrice(getFormattedBigDecimal(30.55), getFormattedBigDecimal(9.17));
    }

    protected Price getPrice(BigDecimal base, BigDecimal luggage) {
        return new Price.Builder()
                .vat(getFormattedBigDecimal(21.00))
                .basePriceIncludingVat(base)
                .luggagePriceIncludingVat(luggage)
                .totalPriceIncludingVat(luggage.add(base))
                .build();
    }

    protected Passenger getPassenger() {
        return getPassenger(1, AgeGroup.ADULT, 2);
    }

    protected Passenger getPassenger(Integer number, AgeGroup ageGroup, Integer luggageItems) {
        return new Passenger.Builder()
                .number(number)
                .name("Luke")
                .surname("Skywalker")
                .ageGroup(ageGroup)
                .luggageItems(luggageItems)
                .build();
    }

    protected PriceCalculationRequest getPriceCalculationRequest() {
        return getPriceCalculationRequest(getPassenger());
    }

    protected PriceCalculationRequest getPriceCalculationRequest(Passenger passenger) {
        return getPriceCalculationRequest(List.of(passenger));
    }

    protected PriceCalculationRequest getPriceCalculationRequest(List<Passenger> passengers) {
        return new PriceCalculationRequest.Builder()
                .routeName(ROUTE_NAME)
                .date(LocalDate.of(2023, 2, 3))
                .passengers(passengers)
                .build();
    }

    protected CalculatedPrices getCalculatedPrices() {
        return new CalculatedPrices(
                getFormattedBigDecimal(21.0),
                getFormattedBigDecimal(12.10),
                getFormattedBigDecimal(6.05),
                getFormattedBigDecimal(3.63)
        );
    }
}
