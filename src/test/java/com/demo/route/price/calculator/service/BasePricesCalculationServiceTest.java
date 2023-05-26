package com.demo.route.price.calculator.service;

import com.demo.route.price.calculator.config.ConfigProperties;
import com.demo.route.price.calculator.service.data.CalculatedPrices;
import com.demo.route.price.calculator.util.AbstractTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasePricesCalculationServiceTest extends AbstractTest {

    private final ConfigProperties configProperties = new ConfigProperties(
            BigDecimal.valueOf(0.5),
            BigDecimal.valueOf(0.3)
    );

    private final BasePricesCalculationService service = new BasePricesCalculationService(configProperties);

    @Test
    void baseCalculatedPrices() {
        //set-up
        BigDecimal vatRate = BigDecimal.valueOf(21.00);
        BigDecimal basePrice = BigDecimal.valueOf(10.00);
        CalculatedPrices expected = getCalculatedPrices();

        //execute
        CalculatedPrices result = service.getBaseCalculatedPrices(vatRate, basePrice);

        //verify
        assertEquals(expected, result);
    }

    @Test
    void baseCalculatedPricesZeroVat() {
        //set-up
        BigDecimal vatRate = BigDecimal.valueOf(0.00);
        BigDecimal basePrice = BigDecimal.valueOf(10.00);
        CalculatedPrices expected = new CalculatedPrices(
                getFormattedBigDecimal(0.0),
                getFormattedBigDecimal(10.0),
                getFormattedBigDecimal(5.0),
                getFormattedBigDecimal(3.0)
        );

        //execute
        CalculatedPrices result = service.getBaseCalculatedPrices(vatRate, basePrice);

        //verify
        assertEquals(expected, result);
    }

}