package com.tangerine.theatre_manager.performance.service.mapper;

import com.tangerine.theatre_manager.performance.repository.model.Performance;
import com.tangerine.theatre_manager.performance.service.dto.PerformanceParam;
import com.tangerine.theatre_manager.performance.service.dto.PerformanceResult;
import org.springframework.stereotype.Component;

@Component
public class PerformanceServiceMapper {

    public Performance paramToDomain(PerformanceParam param) {
        return new Performance(
                param.performanceId(),
                param.performanceName(),
                param.genre(),
                param.ageRate(),
                param.openRun(),
                param.closeRun(),
                param.stage(),
                param.price()
        );
    }

    public PerformanceResult domainToResult(Performance domain) {
        return new PerformanceResult(
                domain.performanceId(),
                domain.performanceName(),
                domain.genre(),
                domain.ageRate(),
                domain.openRun(),
                domain.closeRun(),
                domain.stage(),
                domain.price()
        );
    }

}
