package com.tangerine.theatre_manager.order.service.dto;

import com.tangerine.theatre_manager.order.model.Ticket;
import org.springframework.data.domain.Page;

public record TicketResponses(
        Page<TicketResponse> responses
) {

    public static TicketResponses of(Page<Ticket> responses) {
        return new TicketResponses(responses.map(TicketResponse::of));
    }

}
