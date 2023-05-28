package com.demo.route.price.calculator.service.integration;

import com.demo.route.price.calculator.model.request.PriceCalculationRequest;
import com.demo.route.price.calculator.service.data.ExternalData;

public interface IntegrationService {

    ExternalData asyncObtainPriceCalculationDataFromExternalClients(PriceCalculationRequest request);
}
