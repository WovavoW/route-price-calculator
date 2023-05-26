package com.demo.route.price.calculator;

import com.demo.route.price.calculator.config.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class PriceCalculatorApp {

    public static void main(String... args) {
        SpringApplication.run(PriceCalculatorApp.class, args);
    }
}
