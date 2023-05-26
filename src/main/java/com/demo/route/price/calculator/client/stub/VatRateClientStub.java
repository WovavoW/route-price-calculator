package com.demo.route.price.calculator.client.stub;

import com.demo.route.price.calculator.client.VatRateClient;
import com.demo.route.price.calculator.model.response.VatRateServiceWrappedResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Profile("stub")
public class VatRateClientStub implements VatRateClient {

    public VatRateServiceWrappedResponse getVatRate(
            final LocalDate date
    ) {
        return new VatRateServiceWrappedResponse(
                BigDecimal.valueOf(21)
        );
    }
}