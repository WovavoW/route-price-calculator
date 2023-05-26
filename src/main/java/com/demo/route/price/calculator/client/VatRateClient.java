package com.demo.route.price.calculator.client;

import com.demo.route.price.calculator.model.response.VatRateServiceWrappedResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;


@FeignClient(name = "vat-rate-client", url = "${vat-rate.service.url}", path = "/vat-rate")
public interface VatRateClient {

    @GetMapping
    VatRateServiceWrappedResponse getVatRate(
            @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate date
    );
}
