package com.tangerine.ticketbox.ticket.service.mapper;

import com.tangerine.ticketbox.ticket.repository.dto.TicketEntity;
import com.tangerine.ticketbox.ticket.service.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketServiceMapper {

    TicketServiceMapper INSTANCE = Mappers.getMapper(TicketServiceMapper.class);

    TicketEntity domainToEntity(Ticket domain);

    Ticket entityToDomain(TicketEntity entity);

}
