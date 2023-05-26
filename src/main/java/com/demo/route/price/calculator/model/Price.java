package com.demo.route.price.calculator.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@JsonDeserialize(builder = Price.Builder.class)
public record Price(
        BigDecimal vat,
        BigDecimal basePriceIncludingVat,
        BigDecimal luggagePriceIncludingVat,
        BigDecimal totalPriceIncludingVat
) {
    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        @Nullable
        BigDecimal vat;
        @Nullable
        private BigDecimal basePriceIncludingVat;
        @Nullable
        private BigDecimal luggagePriceIncludingVat;
        @Nullable
        private BigDecimal totalPriceIncludingVat;

        public Builder vat(BigDecimal vat) {
            this.vat = vat;
            return this;
        }

        public Builder basePriceIncludingVat(BigDecimal basePriceIncludingVat) {
            this.basePriceIncludingVat = basePriceIncludingVat;
            return this;
        }

        public Builder luggagePriceIncludingVat(BigDecimal luggagePriceIncludingVat) {
            this.luggagePriceIncludingVat = luggagePriceIncludingVat;
            return this;
        }

        public Builder totalPriceIncludingVat(BigDecimal totalPriceIncludingVat) {
            this.totalPriceIncludingVat = totalPriceIncludingVat;
            return this;
        }

        public Price build() {
            return new Price(
                    vat,
                    basePriceIncludingVat,
                    luggagePriceIncludingVat,
                    totalPriceIncludingVat
            );
        }
    }
}
