package com.demo.route.price.calculator.service.builder;

import com.demo.route.price.calculator.model.Passenger;
import com.demo.route.price.calculator.model.Price;
import com.demo.route.price.calculator.model.Ticket;
import com.demo.route.price.calculator.service.data.CalculatedPrices;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TicketsBuilderService {

    public List<Ticket> buildTicketsForPassengers(List<Passenger> passengers, CalculatedPrices prices) {
        return passengers.stream()
                .map(passenger -> buildTicket(prices, passenger))
                .toList();
    }

    private Ticket buildTicket(CalculatedPrices prices, Passenger passenger) {
        final Price price = buildTicketPrice(prices, passenger);

        return new Ticket(passenger, price);
    }

    private Price buildTicketPrice(CalculatedPrices prices, Passenger passenger) {
        final BigDecimal ticketPrice = determineTicketPrice(prices, passenger);
        final BigDecimal luggagePrice = determineLuggagePrice(prices, passenger);
        final BigDecimal totalPrice = ticketPrice.add(luggagePrice);

        return new Price(
                prices.vatRate(),
                ticketPrice,
                luggagePrice,
                totalPrice
        );
    }

    private BigDecimal determineTicketPrice(CalculatedPrices prices, Passenger passenger) {
        return switch (passenger.ageGroup()) {
            case ADULT -> prices.adultTicketPriceWithVat();
            case CHILD -> prices.childTicketPriceWithVat();
        };
    }

    private BigDecimal determineLuggagePrice(CalculatedPrices prices, Passenger passenger) {
        return (passenger.luggageItems() > 0) ? prices.luggagePriceWithVat().multiply(BigDecimal.valueOf(passenger.luggageItems())) : BigDecimal.ZERO;
    }
}
