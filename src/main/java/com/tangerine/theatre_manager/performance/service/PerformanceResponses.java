package com.tangerine.theatre_manager.performance.service;

import com.tangerine.theatre_manager.performance.model.Performance;
import com.tangerine.theatre_manager.performance.service.dto.PerformanceResponse;
import org.springframework.data.domain.Page;

public record PerformanceResponses(
        Page<PerformanceResponse> responses
) {

    public static PerformanceResponses of(Page<Performance> responses) {
        return new PerformanceResponses(responses.map(PerformanceResponse::of));
    }

}
