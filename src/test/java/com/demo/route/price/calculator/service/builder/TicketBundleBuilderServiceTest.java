package com.demo.route.price.calculator.service.builder;

import com.demo.route.price.calculator.model.Passenger;
import com.demo.route.price.calculator.model.Price;
import com.demo.route.price.calculator.model.Ticket;
import com.demo.route.price.calculator.model.enumerated.AgeGroup;
import com.demo.route.price.calculator.model.response.TicketBundleResponse;
import com.demo.route.price.calculator.util.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TicketBundleBuilderServiceTest extends AbstractTest {

    private final TicketBundleBuilderService ticketBundleBuilderService = new TicketBundleBuilderService();

    @Test
    void buildTicketsBundle() {
        // set-up
        Passenger adult = getPassenger(1, AgeGroup.ADULT, 2);
        Passenger child = getPassenger(2, AgeGroup.CHILD, 1);
        Price adultTicketPrice = getPrice(getFormattedBigDecimal(12.1), getFormattedBigDecimal(7.26));
        Price childTicketPrice = getPrice(getFormattedBigDecimal(6.05), getFormattedBigDecimal(3.63));
        List<Ticket> tickets = List.of(
                getTicket(adult, adultTicketPrice),
                getTicket(child, childTicketPrice)
        );

        // execute
        TicketBundleResponse ticketBundleResponse = ticketBundleBuilderService.buildTicketBundleResponse(ROUTE_NAME, tickets);

        // verify
        assertEquals(ROUTE_NAME, ticketBundleResponse.routeName());
        assertEquals(tickets, ticketBundleResponse.tickets());
        assertEquals(getFormattedBigDecimal(29.04), ticketBundleResponse.totalPrice());
    }

}