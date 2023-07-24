package com.tangerine.theatre_manager.order.controller;


import com.tangerine.theatre_manager.order.controller.dto.TicketOrderRequest;
import com.tangerine.theatre_manager.order.service.TicketOrderService;
import com.tangerine.theatre_manager.order.service.model.TicketOrder;
import com.tangerine.theatre_manager.order.vo.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ticket-orders")
public class TicketOrderRestController {

    private final TicketOrderService service;

    public TicketOrderRestController(TicketOrderService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public void createOrder(@RequestBody TicketOrderRequest request) {
        service.createOrder(TicketOrderControllerMapper.INSTANCE.requestToDomain(request));
    }

    @PostMapping("/delete/{orderId}")
    public void deleteOrderById(@PathVariable UUID orderId) {
        service.deleteOrderById(orderId);
    }

    @GetMapping("/id/{orderId}")
    public ResponseEntity<TicketOrder> orderById(@PathVariable UUID orderId) {
        return ResponseEntity.ok(service.findOrderById(orderId));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<TicketOrder> orderByEmail(@PathVariable Email email) {
        return ResponseEntity.ok(service.findOrderByEmail(email));
    }

}
