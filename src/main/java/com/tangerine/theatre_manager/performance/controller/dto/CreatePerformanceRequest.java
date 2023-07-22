package com.tangerine.theatre_manager.performance.controller.dto;

import com.tangerine.theatre_manager.performance.vo.Price;

import java.time.LocalDate;

public record CreatePerformanceRequest(
        String performanceName,
        String genre,
        String ageRate,
        LocalDate openRun,
        LocalDate closeRun,
        String stage,
        long price
) {
}
