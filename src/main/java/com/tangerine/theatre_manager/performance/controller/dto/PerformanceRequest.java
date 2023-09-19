package com.tangerine.theatre_manager.performance.controller.dto;

import com.tangerine.theatre_manager.global.price.Price;
import com.tangerine.theatre_manager.performance.model.vo.AgeRate;
import com.tangerine.theatre_manager.performance.model.vo.Genre;
import com.tangerine.theatre_manager.performance.model.vo.Stage;
import com.tangerine.theatre_manager.performance.model.vo.Title;
import com.tangerine.theatre_manager.performance.service.dto.PerformanceParam;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record PerformanceRequest(
        @NotBlank String title,
        @NotBlank String genre,
        @NotBlank String ageRate,
        @NotBlank String stage,
        @Positive long price,
        @NotNull LocalDate openRun,
        @NotNull LocalDate closeRun
) {

    public static PerformanceParam to(PerformanceRequest request) {
        return new PerformanceParam(
                new Title(request.title),
                Genre.valueOf(request.genre),
                AgeRate.valueOf(request.ageRate),
                Stage.valueOf(request.stage),
                new Price(request.price),
                LocalDate.from(request.openRun),
                LocalDate.from(request.closeRun)
        );
    }
}
