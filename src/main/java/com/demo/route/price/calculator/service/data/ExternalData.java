package com.demo.route.price.calculator.service.data;

import java.math.BigDecimal;

public record ExternalData(
        BigDecimal vatRate,
        BigDecimal basePrice
) {
}
