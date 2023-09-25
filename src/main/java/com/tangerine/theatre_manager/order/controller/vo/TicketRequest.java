package com.tangerine.theatre_manager.order.controller.vo;

import com.tangerine.theatre_manager.order.service.dto.TicketParam;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record TicketRequest(
        @NotNull @Positive
        Long performanceId,
        @NotNull LocalDate viewDate
) {

    public static TicketParam to(TicketRequest request) {
        return new TicketParam(
                request.performanceId,
                request.viewDate
        );
    }
}
