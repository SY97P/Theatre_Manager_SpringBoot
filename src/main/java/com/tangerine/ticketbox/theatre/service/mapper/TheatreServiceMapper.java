package com.tangerine.ticketbox.theatre.service.mapper;

import com.tangerine.ticketbox.theatre.repository.model.Theatre;
import com.tangerine.ticketbox.theatre.service.dto.TheatreParam;
import com.tangerine.ticketbox.theatre.service.dto.TheatreResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TheatreServiceMapper {

    TheatreServiceMapper INSTANCE = Mappers.getMapper(TheatreServiceMapper.class);

    Theatre paramToDomain(TheatreParam param);

    TheatreResult domainToResult(Theatre domain);

}
