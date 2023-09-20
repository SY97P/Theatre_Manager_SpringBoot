package com.tangerine.theatre_manager.order.controller.vo;

import com.tangerine.theatre_manager.order.service.dto.TicketParam;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TicketRequest(
        @NotNull @Positive
        Long performanceId
) {

    public static TicketParam to(TicketRequest request) {
        return new TicketParam(
                request.performanceId
        );
    }
}
