package com.tangerine.ticketbox.ticket.order.controller.mapper;

import com.tangerine.ticketbox.ticket.order.controller.dto.CreateTicketOrderRequest;
import com.tangerine.ticketbox.ticket.order.controller.dto.TicketOrderResponse;
import com.tangerine.ticketbox.ticket.order.controller.dto.UpdateTicketOrderRequest;
import com.tangerine.ticketbox.ticket.order.service.dto.TicketOrderParam;
import com.tangerine.ticketbox.ticket.order.service.dto.TicketOrderResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketOrderControllerMapper {

    TicketOrderControllerMapper INSTANCE = Mappers.getMapper(TicketOrderControllerMapper.class);

    @Mapping(target = "orderId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "orderedAt", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "ticketOrderStatus", expression = "java(TicketOrderStatus.ACCEPTED)")
    @Mapping(target = "tickets", ignore = true)
    TicketOrderParam requestToParam(CreateTicketOrderRequest request);

    @Mapping(target = "tickets", ignore = true)
    TicketOrderParam requestToParam(UpdateTicketOrderRequest request);

    @Mapping(target = "tickets", ignore = true)
    TicketOrderResponse resultToResponse(TicketOrderResult result);

}
