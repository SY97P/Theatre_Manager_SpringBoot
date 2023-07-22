package com.tangerine.theatre_manager.performance.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

public record UpdatePerformanceRequest(
        UUID performanceId,
        String performanceName,
        String genre,
        String ageRate,
        LocalDate openRun,
        LocalDate closeRun,
        String stage
) {
}