package com.tangerine.theatre_manager.performance.controller.dto;

import com.tangerine.theatre_manager.performance.vo.AgeRate;
import com.tangerine.theatre_manager.performance.vo.Genre;
import com.tangerine.theatre_manager.performance.vo.Stage;

import java.time.LocalDate;
import java.util.UUID;

public record PerformanceResponse(
        UUID performanceId,
        String performanceName,
        Genre genre,
        AgeRate ageRate,
        LocalDate openRun,
        LocalDate closeRun,
        Stage stage
) {
}
