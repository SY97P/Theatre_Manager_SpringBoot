package com.tangerine.theatre_manager.performance.vo;

import com.tangerine.theatre_manager.global.exception.InvalidDataException;

public record Price(long priceValue) {
    public Price {
        validatePositive(priceValue);
    }

    private void validatePositive(long priceValue) {
        if (priceValue < 0) {
            throw new InvalidDataException("Price value is lower than 0");
        }
    }
}
