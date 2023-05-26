package com.demo.route.price.calculator.service.data;

import java.math.BigDecimal;

public record CalculatedPrices(
        BigDecimal vatRate,
        BigDecimal adultTicketPriceWithVat,
        BigDecimal childTicketPriceWithVat,
        BigDecimal luggagePriceWithVat
) {
}
