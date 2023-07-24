package com.tangerine.theatre_manager.order.ticket.controller.mapper;

import com.tangerine.theatre_manager.order.ticket.controller.dto.TicketRequest;
import com.tangerine.theatre_manager.order.ticket.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketControllerMapper {

    TicketControllerMapper INSTANCE = Mappers.getMapper(TicketControllerMapper.class);

    @Mapping(target = "ticketId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(source = "ticketPrice", target = "ticketPrice.priceValue")
    Ticket requestToDomain(TicketRequest request);

}
