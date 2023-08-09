package com.tangerine.theatre_manager.performance.controller.mapper;

import com.tangerine.theatre_manager.global.generator.IdGenerator;
import com.tangerine.theatre_manager.performance.controller.dto.CreatePerformanceRequest;
import com.tangerine.theatre_manager.performance.controller.dto.PerformanceResponse;
import com.tangerine.theatre_manager.performance.controller.dto.UpdatePerformanceRequest;
import com.tangerine.theatre_manager.performance.service.dto.PerformanceParam;
import com.tangerine.theatre_manager.performance.service.dto.PerformanceResult;
import com.tangerine.theatre_manager.performance.vo.*;
import org.springframework.stereotype.Component;

@Component
public class PerformanceControllerMapper {

    private final IdGenerator idGenerator;

    public PerformanceControllerMapper(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public PerformanceParam requestToParam(CreatePerformanceRequest request) {
        return new PerformanceParam(
                idGenerator.getGeneratedId(),
                new PerformanceName(request.performanceName()),
                Genre.valueOf(request.genre()),
                AgeRate.valueOf(request.ageRate()),
                request.openRun(),
                request.closeRun(),
                Stage.valueOf(request.stage()),
                new Price(request.price())
        );
    }

    public PerformanceParam requestToParam(UpdatePerformanceRequest request) {
        return new PerformanceParam(
                request.performanceId(),
                new PerformanceName(request.performanceName()),
                Genre.valueOf(request.genre()),
                AgeRate.valueOf(request.ageRate()),
                request.openRun(),
                request.closeRun(),
                Stage.valueOf(request.stage()),
                new Price(request.price())
        );
    }

    public PerformanceResponse resultToResponse(PerformanceResult result) {
        return new PerformanceResponse(
                result.performanceId(),
                result.performanceName().performanceNameValue(),
                result.genre(),
                result.ageRate(),
                result.openRun(),
                result.closeRun(),
                result.stage(),
                result.price().priceValue()
        );
    }

}
