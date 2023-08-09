package com.tangerine.theatre_manager.order.ticket.controller.mapper;

import com.tangerine.theatre_manager.global.generator.IdGenerator;
import com.tangerine.theatre_manager.order.ticket.controller.dto.TicketRequest;
import com.tangerine.theatre_manager.order.ticket.model.Ticket;
import com.tangerine.theatre_manager.performance.vo.Price;
import org.springframework.stereotype.Component;

@Component
public class TicketControllerMapper {

    private final IdGenerator idGenerator;

    public TicketControllerMapper(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Ticket requestToDomain(TicketRequest request) {
        return new Ticket(
                idGenerator.getGeneratedId(),
                request.orderId(),
                new Price(request.ticketPrice()),
                request.reservedDate()
        );
    }

}
