package com.demo.route.price.calculator.service.builder;

import com.demo.route.price.calculator.model.Ticket;
import com.demo.route.price.calculator.model.response.TicketBundleResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TicketBundleBuilderService {

    public TicketBundleResponse buildTicketBundleResponse(String routeName, List<Ticket> tickets) {
        final BigDecimal totalPrice = determineBundleTotalPrice(tickets);

        return new TicketBundleResponse(
                routeName,
                tickets,
                totalPrice
        );
    }

    private BigDecimal determineBundleTotalPrice(List<Ticket> tickets) {
        return tickets.stream()
                .map(ticket -> ticket.price().totalPriceIncludingVat())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
