package com.tangerine.theatre_manager.performance.vo;

import com.tangerine.theatre_manager.global.exception.InvalidDataException;

public record PerformanceName(String performanceNameValue) {

    public PerformanceName {
        validate(performanceNameValue);
    }

    private void validate(String performanceNameValue) {
        if (performanceNameValue == null || performanceNameValue.isBlank()) {
            throw new InvalidDataException("Performance name is invalid");
        }
    }
}
