package com.demo.route.price.calculator.util;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResourceHelper {
    private final static ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();

    public static String readFileFromResources(String path) {
        try {
            return RESOURCE_LOADER.getResource(String.format("classpath:%s", path)).getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
