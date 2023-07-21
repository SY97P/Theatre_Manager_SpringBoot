package com.tangerine.ticketbox.theatre.controller.mapper;

import com.tangerine.ticketbox.theatre.controller.dto.CreateTheatreRequest;
import com.tangerine.ticketbox.theatre.controller.dto.TheatreResponse;
import com.tangerine.ticketbox.theatre.controller.dto.UpdateTheatreRequest;
import com.tangerine.ticketbox.theatre.service.dto.TheatreParam;
import com.tangerine.ticketbox.theatre.service.dto.TheatreResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TheatreControllerMapper {

    TheatreControllerMapper INSTANCE = Mappers.getMapper(TheatreControllerMapper.class);

    @Mapping(target = "theatreId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(source = "theatreName", target = "theatreName.theatreNameValue")
    TheatreParam requestToParam(CreateTheatreRequest request);

    @Mapping(source = "theatreName", target = "theatreName.theatreNameValue")
    TheatreParam requestToParam(UpdateTheatreRequest request);

    @Mapping(source = "theatreName.theatreNameValue", target = "theatreName")
    TheatreResponse resultToResponse(TheatreResult result);

}
