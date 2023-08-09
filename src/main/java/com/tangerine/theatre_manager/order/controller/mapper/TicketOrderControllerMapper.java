package com.tangerine.theatre_manager.order.controller.mapper;

import com.tangerine.theatre_manager.order.controller.dto.TicketOrderRequest;
import com.tangerine.theatre_manager.order.service.model.TicketOrder;
import com.tangerine.theatre_manager.order.ticket.controller.dto.TicketRequest;
import com.tangerine.theatre_manager.order.ticket.model.Ticket;
import com.tangerine.theatre_manager.order.vo.Email;
import com.tangerine.theatre_manager.order.vo.TicketOrderStatus;
import com.tangerine.theatre_manager.performance.vo.Price;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TicketOrderControllerMapper {

    public TicketOrder requestToDomain(TicketOrderRequest request) {
        return new TicketOrder(
                request.orderId(),
                new Email(request.email()),
                LocalDate.now(),
                TicketOrderStatus.ACCEPTED,
                toTickets(request.tickets())
        );
    }

    public Ticket toTicket(TicketRequest request) {
        return new Ticket(
                request.orderId(),
                request.orderId(),
                new Price(request.ticketPrice()),
                request.reservedDate()
        );
    }

    public List<Ticket> toTickets(List<TicketRequest> requests) {
        return requests.stream()
                .map(this::toTicket)
                .toList();
    }

}
