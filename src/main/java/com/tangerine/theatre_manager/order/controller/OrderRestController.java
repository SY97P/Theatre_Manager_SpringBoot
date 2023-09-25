package com.tangerine.theatre_manager.order.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.tangerine.theatre_manager.order.controller.vo.OrderRequest;
import com.tangerine.theatre_manager.order.facade.OrderFacadeService;
import com.tangerine.theatre_manager.order.service.OrderService;
import com.tangerine.theatre_manager.order.service.dto.OrderResponse;
import com.tangerine.theatre_manager.order.service.dto.TicketResponses;
import java.net.URI;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/orders", produces = APPLICATION_JSON_VALUE)
public class OrderRestController {

    private final OrderService orderService;
    private final OrderFacadeService orderFacadeService;

    public OrderRestController(OrderService orderService, OrderFacadeService orderFacadeService) {
        this.orderService = orderService;
        this.orderFacadeService = orderFacadeService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> createOrder(
            @Validated @RequestBody OrderRequest request
    ) {
        OrderResponse response = orderFacadeService.registerOrder(OrderRequest.to(request));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{orderId}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity
                .status(CREATED)
                .location(location)
                .body(response);
    }

    @PatchMapping(path = "/{orderId}/change-status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long orderId
    ) {
        OrderResponse response = orderService.editOrderStatus(orderId);
        return ResponseEntity
                .status(OK)
                .body(response);
    }

    @DeleteMapping(path = "/{orderId}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable Long orderId
    ) {
        orderService.removeOrder(orderId);
        return ResponseEntity
                .status(NO_CONTENT)
                .build();
    }

    @GetMapping(path = "/{orderId}")
    public ResponseEntity<OrderResponse> readOrder(
            @PathVariable Long orderId
    ) {
        OrderResponse response = orderService.findOrder(orderId);
        return ResponseEntity
                .status(OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<TicketResponses> readTicketsOfPerformance(
            @RequestParam Long performanceId,
            Pageable pageable
    ) {
        TicketResponses responses = orderService.findTicketsOfPerformance(performanceId, pageable);
        return ResponseEntity
                .status(OK)
                .body(responses);
    }

    @GetMapping(path = "/me")
    public ResponseEntity<OrderResponses> readUserOrders(
            Pageable pageable
    ) {
        OrderResponses responses = orderFacadeService.findUserOrders(pageable);
        return ResponseEntity
                .status(OK)
                .body(responses);
    }
}
