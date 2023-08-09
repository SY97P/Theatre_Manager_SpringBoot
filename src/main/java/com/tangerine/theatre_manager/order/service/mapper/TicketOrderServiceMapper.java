package com.tangerine.theatre_manager.order.service.mapper;

import com.tangerine.theatre_manager.order.repository.model.TicketOrderEntity;
import com.tangerine.theatre_manager.order.service.model.TicketOrder;
import org.springframework.stereotype.Component;

@Component
public class TicketOrderServiceMapper {

    public TicketOrderEntity domainToEntity(TicketOrder domain) {
        return new TicketOrderEntity(
                domain.orderId(),
                domain.email(),
                domain.orderedAt(),
                domain.ticketOrderStatus()
        );
    }

}
