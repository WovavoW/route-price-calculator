package com.demo.route.price.calculator.service.builder;

import com.demo.route.price.calculator.model.Passenger;
import com.demo.route.price.calculator.model.Price;
import com.demo.route.price.calculator.model.Ticket;
import com.demo.route.price.calculator.model.enumerated.AgeGroup;
import com.demo.route.price.calculator.service.data.CalculatedPrices;
import com.demo.route.price.calculator.util.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TicketsBuilderServiceTest extends AbstractTest {

    private final TicketsBuilderService ticketsBuilderService = new TicketsBuilderService();

    @Test
    void buildTickets() {
        // set-up
        Passenger adult = getPassenger(1, AgeGroup.ADULT, 2);
        Passenger child = getPassenger(2, AgeGroup.CHILD, 1);
        List<Passenger> passengers = List.of(
                adult,
                child
        );
        CalculatedPrices calculatedPrices = getCalculatedPrices();
        Price adultTicketExpectedPrice = getPrice(getFormattedBigDecimal(12.1), getFormattedBigDecimal(7.26));
        Price childTicketExpectedPrice = getPrice(getFormattedBigDecimal(6.05), getFormattedBigDecimal(3.63));
        List<Ticket> expected = List.of(
                getTicket(adult, adultTicketExpectedPrice),
                getTicket(child, childTicketExpectedPrice)
        );

        // execute
        List<Ticket> tickets = ticketsBuilderService.buildTicketsForPassengers(passengers, calculatedPrices);

        // verify
        assertEquals(expected, tickets);
    }

}