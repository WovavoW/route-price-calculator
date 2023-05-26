package com.demo.route.price.calculator.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableFeignClients(basePackages = "com.demo")
@Profile("!stub")
public class FeignClientConfiguration {
}