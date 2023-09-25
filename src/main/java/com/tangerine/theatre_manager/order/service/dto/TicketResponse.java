package com.tangerine.theatre_manager.order.service.dto;

import com.tangerine.theatre_manager.order.model.Ticket;

public record TicketResponse(
        Long id,
        long price,
        Long orderId,
        Long performanceId
) {

    public static TicketResponse of(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getPerformance().getPriceValue(),
                ticket.getOrder().getId(),
                ticket.getPerformance().getId()
        );
    }
}
