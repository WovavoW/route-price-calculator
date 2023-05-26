package com.demo.route.price.calculator.model.response;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;

public record BasePriceServiceWrappedResponse(
        @NonNull
        BigDecimal basePrice
) {
}
