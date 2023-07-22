package com.tangerine.theatre_manager.performance.service.mapper;

import com.tangerine.theatre_manager.performance.repository.model.Performance;
import com.tangerine.theatre_manager.performance.service.dto.PerformanceParam;
import com.tangerine.theatre_manager.performance.service.dto.PerformanceResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PerformanceServiceMapper {

    PerformanceServiceMapper INSTANCE = Mappers.getMapper(PerformanceServiceMapper.class);

    Performance paramToDomain(PerformanceParam param);

    PerformanceResult domainToResult(Performance domain);

}
