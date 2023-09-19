package com.tangerine.theatre_manager.performance.service.dto;

import com.tangerine.theatre_manager.global.price.Price;
import com.tangerine.theatre_manager.performance.model.Performance;
import com.tangerine.theatre_manager.performance.model.vo.AgeRate;
import com.tangerine.theatre_manager.performance.model.vo.Genre;
import com.tangerine.theatre_manager.performance.model.vo.Stage;
import com.tangerine.theatre_manager.performance.model.vo.Title;
import java.time.LocalDate;

public record PerformanceParam(
        Title title,
        Genre genre,
        AgeRate ageRate,
        Stage stage,
        Price price,
        LocalDate openRun,
        LocalDate closeRun
) {

    public static Performance to(PerformanceParam param) {
        return new Performance(
                param.title,
                param.genre,
                param.ageRate,
                param.stage,
                param.price,
                param.openRun,
                param.closeRun
        );
    }
}
