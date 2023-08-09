package com.tangerine.theatre_manager.performance;

import com.tangerine.theatre_manager.performance.repository.model.Performance;
import com.tangerine.theatre_manager.performance.service.dto.PerformanceParam;
import com.tangerine.theatre_manager.performance.service.mapper.PerformanceServiceMapper;
import com.tangerine.theatre_manager.performance.vo.*;
import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class PerformanceTestData {
    
    private static final PerformanceServiceMapper mapper = new PerformanceServiceMapper();

    public static Performance newDomain(Performance domain) {
        return new Performance(domain.performanceId(), new PerformanceName("새로운 연극"), Genre.PLAY, AgeRate.ALL, LocalDate.of(2023, 4, 12), LocalDate.of(2023, 9, 1), Stage.B1, new Price(1000));
    }

    public static PerformanceParam newParams(PerformanceParam param) {
        return new PerformanceParam(param.performanceId(), new PerformanceName("새로운 연극"), Genre.PLAY, AgeRate.ALL, LocalDate.of(2023, 4, 12), LocalDate.of(2023, 9, 1), Stage.B1, new Price(1000));
    }

    public static List<PerformanceParam> performanceParams = List.of(
            new PerformanceParam(UUID.randomUUID(), new PerformanceName("밤의 여왕 아리아"), Genre.OPERA, AgeRate.ALL, LocalDate.of(2023, 1, 23), LocalDate.of(2023, 8, 14), Stage.A1, new Price(1000)),
            new PerformanceParam(UUID.randomUUID(), new PerformanceName("쉬어매드니스"), Genre.PLAY, AgeRate.FIFTEEN, LocalDate.of(2022, 6, 1), LocalDate.of(2023, 10, 31), Stage.A2, new Price(1000)),
            new PerformanceParam(UUID.randomUUID(), new PerformanceName("시라노"), Genre.MUSICAL, AgeRate.ADULT_ONLY, LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 1), Stage.B1, new Price(1000)),
            new PerformanceParam(UUID.randomUUID(), new PerformanceName("라면"), Genre.PLAY, AgeRate.FIFTEEN, LocalDate.of(2023, 10, 23), LocalDate.of(2024, 3, 20), Stage.B2, new Price(1000))
    );

    public static List<Performance> performanceDomains = performanceParams.stream()
            .map(mapper::paramToDomain)
            .toList();

    static Stream<Arguments> provideParams() {
        return performanceParams.stream()
                .map(Arguments::of);
    }

    static Stream<Arguments> provideDomains() {
        return performanceParams.stream()
                .map(mapper::paramToDomain)
                .map(Arguments::of);
    }
}
