package com.tangerine.theatre_manager.performance.controller.mapper;

import com.tangerine.theatre_manager.performance.controller.dto.CreatePerformanceRequest;
import com.tangerine.theatre_manager.performance.controller.dto.PerformanceResponse;
import com.tangerine.theatre_manager.performance.controller.dto.UpdatePerformanceRequest;
import com.tangerine.theatre_manager.performance.service.dto.PerformanceParam;
import com.tangerine.theatre_manager.performance.service.dto.PerformanceResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PerformanceControllerMapper {

    PerformanceControllerMapper INSTANCE = Mappers.getMapper(PerformanceControllerMapper.class);

    @Mapping(target = "performanceId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(source = "performanceName", target = "performanceName.performanceNameValue")
    @Mapping(source = "price", target = "price.priceValue")
    PerformanceParam requestToParam(CreatePerformanceRequest request);

    @Mapping(source = "performanceName", target = "performanceName.performanceNameValue")
    @Mapping(source = "price", target = "price.priceValue")
    PerformanceParam requestToParam(UpdatePerformanceRequest request);

    @Mapping(source = "performanceName.performanceNameValue", target = "performanceName")
    @Mapping(source = "price.priceValue", target = "price")
    PerformanceResponse resultToResponse(PerformanceResult result);

}
