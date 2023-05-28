package com.demo.route.price.calculator.controller.advice;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String path

) {
    public static final class Builder {
        LocalDateTime timestamp;
        Integer status;
        String error;
        String path;

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder status(Integer status) {
            this.status = status;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(
                    timestamp,
                    status,
                    error,
                    path
            );
        }
    }
}