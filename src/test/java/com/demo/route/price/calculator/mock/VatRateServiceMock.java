package com.demo.route.price.calculator.mock;

import com.demo.route.price.calculator.util.ResourceHelper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static com.demo.route.price.calculator.constant.Constants.INVALID_DATE;
import static com.demo.route.price.calculator.constant.Constants.VALID_DATE;

public class VatRateServiceMock {

    public static void setupMockResponses(WireMockServer mockService) {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo(urlPattern(VALID_DATE)))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(ResourceHelper.readFileFromResources("mock/response/vat-rate-response.json"))
                )
        );

        mockService.stubFor(WireMock.get(WireMock.urlEqualTo(urlPattern(INVALID_DATE)))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                )
        );
    }

    private static String urlPattern(LocalDate date) {
        return String.format("/vat-rate?date=%s", date);
    }

}
