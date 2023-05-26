package com.demo.route.price.calculator.mock;

import com.demo.route.price.calculator.util.ResourceHelper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.demo.route.price.calculator.constant.Constants.*;

public class BasePriceServiceMock {

    public static void setupMockResponses(WireMockServer mockService) {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo(urlPattern(VALID_ROUTE)))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(ResourceHelper.readFileFromResources("mock/response/base-price-response.json"))
                )
        );

        mockService.stubFor(WireMock.get(WireMock.urlEqualTo(urlPattern(INVALID_ROUTE)))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                )
        );
    }

    private static String urlPattern(String route) {
        return String.format("/base-price?routeName=%s&date=%s", route, VALID_DATE);
    }

}
