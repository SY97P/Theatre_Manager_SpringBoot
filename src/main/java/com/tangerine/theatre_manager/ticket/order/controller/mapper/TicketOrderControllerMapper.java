package com.tangerine.theatre_manager.ticket.order.controller.mapper;

import com.tangerine.theatre_manager.ticket.order.controller.dto.CreateTicketOrderRequest;
import com.tangerine.theatre_manager.ticket.order.controller.dto.TicketOrderResponse;
import com.tangerine.theatre_manager.ticket.order.controller.dto.UpdateTicketOrderRequest;
import com.tangerine.theatre_manager.ticket.order.service.dto.TicketOrderParam;
import com.tangerine.theatre_manager.ticket.order.service.dto.TicketOrderResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketOrderControllerMapper {

    TicketOrderControllerMapper INSTANCE = Mappers.getMapper(TicketOrderControllerMapper.class);

    @Mapping(target = "orderId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(source = "email", target = "email.emailAddress")
    @Mapping(target = "orderedAt", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "ticketOrderStatus", expression = "java(TicketOrderStatus.ACCEPTED)")
    @Mapping(target = "tickets", ignore = true)
    TicketOrderParam requestToParam(CreateTicketOrderRequest request);

    @Mapping(source = "email", target = "email.emailAddress")
    @Mapping(target = "tickets", ignore = true)
    TicketOrderParam requestToParam(UpdateTicketOrderRequest request);

    @Mapping(source = "email.emailAddress", target = "email")
    @Mapping(target = "tickets", ignore = true)
    TicketOrderResponse resultToResponse(TicketOrderResult result);

}
