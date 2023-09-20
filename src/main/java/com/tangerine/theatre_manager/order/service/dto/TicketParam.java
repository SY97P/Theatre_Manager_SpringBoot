package com.tangerine.theatre_manager.order.service.dto;

import com.tangerine.theatre_manager.order.model.Order;
import com.tangerine.theatre_manager.order.model.Ticket;
import com.tangerine.theatre_manager.performance.model.Performance;

public record TicketParam(
        Long performanceId
) {

    public static Ticket to(Order order, Performance performance) {
        return new Ticket(
                performance.getPrice(),
                order,
                performance
        );
    }

}
