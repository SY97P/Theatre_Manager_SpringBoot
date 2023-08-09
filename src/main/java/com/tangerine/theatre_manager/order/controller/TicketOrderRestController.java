package com.tangerine.theatre_manager.order.controller;


import com.tangerine.theatre_manager.order.controller.dto.TicketOrderRequest;
import com.tangerine.theatre_manager.order.controller.mapper.TicketOrderControllerMapper;
import com.tangerine.theatre_manager.order.service.TicketOrderService;
import com.tangerine.theatre_manager.order.service.model.TicketOrder;
import com.tangerine.theatre_manager.order.vo.Email;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ticket-orders")
public class TicketOrderRestController {

    private final TicketOrderService service;
    private final TicketOrderControllerMapper mapper;

    public TicketOrderRestController(TicketOrderService service, TicketOrderControllerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createOrder(@RequestBody TicketOrderRequest request) {
        service.createOrder(mapper.requestToDomain(request));
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrderById(@PathVariable UUID orderId) {
        service.deleteOrderById(orderId);
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
