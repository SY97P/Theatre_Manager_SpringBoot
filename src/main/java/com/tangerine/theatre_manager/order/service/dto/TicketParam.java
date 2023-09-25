package com.tangerine.theatre_manager.order.service.dto;

import com.tangerine.theatre_manager.order.model.Order;
import com.tangerine.theatre_manager.order.model.Ticket;
import com.tangerine.theatre_manager.performance.model.Performance;
import java.time.LocalDate;

public record TicketParam(
        Long performanceId,
        LocalDate viewDate
) {

    public static Ticket to(TicketParam param, Order order, Performance performance) {
        return new Ticket(
                performance.getPrice(),
                param.viewDate,
                order,
                performance
        );
    }

}
