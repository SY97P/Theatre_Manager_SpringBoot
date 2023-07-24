package com.tangerine.theatre_manager.order.controller;

import com.tangerine.theatre_manager.order.controller.dto.TicketOrderRequest;
import com.tangerine.theatre_manager.order.service.model.TicketOrder;
import com.tangerine.theatre_manager.order.ticket.controller.dto.TicketRequest;
import com.tangerine.theatre_manager.order.ticket.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketOrderControllerMapper {

    TicketOrderControllerMapper INSTANCE = Mappers.getMapper(TicketOrderControllerMapper.class);

    @Mapping(source = "email", target = "email.emailAddress")
    @Mapping(target = "orderedAt", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "ticketOrderStatus", expression = "java(TicketOrderStatus.ACCEPTED)")
    TicketOrder requestToDomain(TicketOrderRequest request);

    @Mapping(target = "ticketId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(source = "ticketPrice", target = "ticketPrice.priceValue")
    Ticket toTicket(TicketRequest request);

    List<Ticket> toTickets(List<TicketRequest> requests);

}
