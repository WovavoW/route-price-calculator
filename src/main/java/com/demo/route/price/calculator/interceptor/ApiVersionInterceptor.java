package com.demo.route.price.calculator.interceptor;

import com.demo.route.price.calculator.controller.annotation.ValidateApiVersion;
import com.demo.route.price.calculator.exception.InvalidRequestException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ApiVersionInterceptor implements HandlerInterceptor {

    public static final String ACCEPT_VERSION_HEADER = "Accept-version";

    private final String apiVersion;

    public ApiVersionInterceptor(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            ValidateApiVersion annotation = handlerMethod.getMethodAnnotation(ValidateApiVersion.class);
            if (annotation != null) {
                String acceptVersion = request.getHeader(ACCEPT_VERSION_HEADER);
                if (!apiVersion.equals(acceptVersion)) {
                    throw new InvalidRequestException("Invalid API version. Expected: " + apiVersion);
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        // no need to implement
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) {
        // no need to implement
    }
}