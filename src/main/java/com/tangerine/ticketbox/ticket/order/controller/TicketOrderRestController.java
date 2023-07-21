package com.tangerine.ticketbox.ticket.order.controller;

import com.tangerine.ticketbox.ticket.order.controller.dto.CreateTicketOrderRequest;
import com.tangerine.ticketbox.ticket.order.controller.dto.TicketOrderResponse;
import com.tangerine.ticketbox.ticket.order.controller.dto.UpdateTicketOrderRequest;
import com.tangerine.ticketbox.ticket.order.controller.mapper.TicketOrderControllerMapper;
import com.tangerine.ticketbox.ticket.order.vo.Email;
import com.tangerine.ticketbox.ticket.order.service.TicketOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class TicketOrderRestController {

    private final TicketOrderService service;

    public TicketOrderRestController(TicketOrderService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public void createOrder(@RequestBody CreateTicketOrderRequest request) {
        service.createOrder(TicketOrderControllerMapper.INSTANCE.requestToParam(request));
    }

    @PostMapping("/update")
    public void updateOrder(@RequestBody UpdateTicketOrderRequest request) {
        service.updateOrder(TicketOrderControllerMapper.INSTANCE.requestToParam(request));
    }

    @DeleteMapping("/delete/all")
    public void deleteAllOrder() {
        service.deleteAllOrders();
    }

    @DeleteMapping("/delete/{orderId}")
    public void deleteOrderById(@PathVariable UUID orderId) {
        service.deleteOrderById(orderId);
    }

    @GetMapping("")
    public ResponseEntity<List<TicketOrderResponse>> orderList() {
        return ResponseEntity.ok(
                service.findAllOrders()
                        .stream()
                        .map(TicketOrderControllerMapper.INSTANCE::resultToResponse)
                        .toList()
        );
    }

    @GetMapping("/id/{orderId}")
    public ResponseEntity<TicketOrderResponse> orderById(@PathVariable UUID orderId) {
        return ResponseEntity.ok(TicketOrderControllerMapper.INSTANCE.resultToResponse(service.findOrderById(orderId)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<TicketOrderResponse> orderByEmail(@PathVariable Email email) {
        return ResponseEntity.ok(TicketOrderControllerMapper.INSTANCE.resultToResponse(service.findOrderByEmail(email)));
    }

}
