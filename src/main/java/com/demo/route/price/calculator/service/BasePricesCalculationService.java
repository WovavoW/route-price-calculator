package com.demo.route.price.calculator.service;

import com.demo.route.price.calculator.config.ConfigProperties;
import com.demo.route.price.calculator.service.data.CalculatedPrices;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class BasePricesCalculationService {

    private final ConfigProperties configProperties;

    public BasePricesCalculationService(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    public CalculatedPrices getBaseCalculatedPrices(BigDecimal vatRate, BigDecimal basePrice) {
        final BigDecimal applicableVatRate = vatRate.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP).add(BigDecimal.ONE);

        return determinePrices(vatRate, basePrice, applicableVatRate);
    }

    private CalculatedPrices determinePrices(BigDecimal vatRate, BigDecimal basePrice, BigDecimal applicableVatRate) {
        final BigDecimal adjustedVatRate = vatRate.setScale(2, RoundingMode.HALF_UP);
        final BigDecimal adultTicketPriceWithVat = calculateAdultTicketPriceWithVat(basePrice, applicableVatRate);
        final BigDecimal childTicketPriceWithVat = calculateChildTicketPriceWithVat(basePrice, applicableVatRate);
        final BigDecimal luggagePriceWithVat = calculateLuggageTicketPriceWithVat(basePrice, applicableVatRate);

        return new CalculatedPrices(
                adjustedVatRate,
                adultTicketPriceWithVat,
                childTicketPriceWithVat,
                luggagePriceWithVat
        );
    }

    private BigDecimal calculateAdultTicketPriceWithVat(BigDecimal basePrice, BigDecimal applicableVatRate) {
        return basePrice.multiply(applicableVatRate).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateChildTicketPriceWithVat(BigDecimal basePrice, BigDecimal applicableVatRate) {
        return calculateAdultTicketPriceWithVat(basePrice.multiply(configProperties.childFraction()), applicableVatRate);
    }

    private BigDecimal calculateLuggageTicketPriceWithVat(BigDecimal basePrice, BigDecimal applicableVatRate) {
        return calculateAdultTicketPriceWithVat(basePrice.multiply(configProperties.luggageFraction()), applicableVatRate);
    }
}
