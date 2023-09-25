package com.tangerine.theatre_manager.order.controller.vo;

import com.tangerine.theatre_manager.order.model.vo.OrderStatus;
import com.tangerine.theatre_manager.order.service.dto.OrderParam;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OrderRequest(
        @NotNull @NotEmpty
        List<TicketRequest> tickets
) {

    public static OrderParam to(OrderRequest request) {
        return new OrderParam(
                OrderStatus.ACCEPTED,
                request.tickets.stream()
                        .map(TicketRequest::to)
                        .toList()
        );
    }
}
