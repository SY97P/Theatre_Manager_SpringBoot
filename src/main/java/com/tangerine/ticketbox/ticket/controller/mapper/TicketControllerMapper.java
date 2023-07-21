package com.tangerine.ticketbox.ticket.controller.mapper;

import com.tangerine.ticketbox.ticket.controller.dto.CreateTicketRequest;
import com.tangerine.ticketbox.ticket.controller.dto.TicketResponse;
import com.tangerine.ticketbox.ticket.controller.dto.UpdateTicketRequest;
import com.tangerine.ticketbox.ticket.service.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketControllerMapper {

    TicketControllerMapper INSTANCE = Mappers.getMapper(TicketControllerMapper.class);

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "ticketId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(source = "ticketPrice", target = "ticketPrice.priceValue")
    @Mapping(source = "ticketQuantity", target = "ticketQuantity.quantityValue")
    Ticket requestToDomain(CreateTicketRequest request);

    @Mapping(source = "ticketPrice", target = "ticketPrice.priceValue")
    @Mapping(source = "ticketQuantity", target = "ticketQuantity.quantityValue")
    Ticket requestToDomain(UpdateTicketRequest request);

    @Mapping(source = "ticketPrice.priceValue", target = "ticketPrice")
    @Mapping(source = "ticketQuantity.quantityValue", target = "ticketQuantity")
    TicketResponse domainToResponse(Ticket domain);

}
