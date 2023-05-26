package com.demo.route.price.calculator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "price.config")
@ConfigurationPropertiesScan
public record ConfigProperties(
        BigDecimal childFraction,
        BigDecimal luggageFraction
) {
}
