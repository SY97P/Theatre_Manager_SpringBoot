package com.tangerine.theatre_manager.order.controller;


import com.tangerine.theatre_manager.global.response.SuccessCode;
import com.tangerine.theatre_manager.order.controller.dto.TicketOrderRequest;
import com.tangerine.theatre_manager.order.controller.mapper.TicketOrderControllerMapper;
import com.tangerine.theatre_manager.order.service.TicketOrderService;
import com.tangerine.theatre_manager.order.service.model.TicketOrder;
import com.tangerine.theatre_manager.order.vo.Email;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ticket-orders")
public class TicketOrderRestController {

    private final TicketOrderService service;
    private final TicketOrderControllerMapper mapper;

    public TicketOrderRestController(TicketOrderService service, TicketOrderControllerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOrder(@RequestBody TicketOrderRequest request) {
        UUID orderId = service.createOrder(mapper.requestToDomain(request));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MessageFormat.format("{0} {1}", orderId, SuccessCode.TICKET_ORDER_SAVE_SUCCESS));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable UUID orderId) {
        UUID deletedId = service.deleteOrderById(orderId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(MessageFormat.format("{0} {1}", deletedId, SuccessCode.TICKET_ORDER_DELETE_SUCCESS));
    }

    @GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketOrder> orderById(@PathVariable UUID orderId) {
        return ResponseEntity.ok(service.findOrderById(orderId));
    }

    @GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketOrder> orderByEmail(@PathVariable Email email) {
        return ResponseEntity.ok(service.findOrderByEmail(email));
    }

}
