package com.tangerine.theatre_manager.performance.service.dto;

import com.tangerine.theatre_manager.performance.model.Performance;
import java.time.LocalDate;

public record PerformanceResponse(
        Long id,
        String title,
        String genre,
        String ageRate,
        String stage,
        long price,
        LocalDate openRun,
        LocalDate closeRun
) {

    public static PerformanceResponse of(Performance performance) {
        return new PerformanceResponse(
                performance.getId(),
                performance.getTitleValue(),
                performance.getGenreName(),
                performance.getAgeRateName(),
                performance.getStageName(),
                performance.getPriceValue(),
                performance.getOpenRun(),
                performance.getCloseRun()
        );
    }
}
