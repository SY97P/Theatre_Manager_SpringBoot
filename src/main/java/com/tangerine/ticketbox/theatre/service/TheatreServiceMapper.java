package com.tangerine.ticketbox.theatre.service;

import com.tangerine.ticketbox.theatre.model.Theatre;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TheatreServiceMapper {

    TheatreServiceMapper INSTANCE = Mappers.getMapper(TheatreServiceMapper.class);

    Theatre paramToDomain(TheatreParam param);

    TheatreResult domainToResult(Theatre domain);

}
