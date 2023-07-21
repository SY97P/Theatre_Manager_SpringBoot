package com.tangerine.ticketbox.ticket.order.controller;

import com.tangerine.ticketbox.ticket.controller.mapper.TicketControllerMapper;
import com.tangerine.ticketbox.ticket.order.controller.dto.CreateTicketOrderRequest;
import com.tangerine.ticketbox.ticket.order.controller.dto.TicketOrderResponse;
import com.tangerine.ticketbox.ticket.order.controller.dto.UpdateTicketOrderRequest;
import com.tangerine.ticketbox.ticket.order.controller.mapper.TicketOrderControllerMapper;
import com.tangerine.ticketbox.ticket.order.service.TicketOrderService;
import com.tangerine.ticketbox.ticket.order.service.dto.TicketOrderParam;
import com.tangerine.ticketbox.ticket.order.service.dto.TicketOrderResult;
import com.tangerine.ticketbox.ticket.order.vo.Email;
import com.tangerine.ticketbox.ticket.service.model.Ticket;
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
        TicketOrderParam ticketOrderParam = TicketOrderControllerMapper.INSTANCE.requestToParam(request);
        List<Ticket> tickets = request.tickets().stream()
                .map(TicketControllerMapper.INSTANCE::requestToDomain)
                .toList();
        tickets.forEach(ticket -> ticket.setOrderId(ticketOrderParam.getOrderId()));
        ticketOrderParam.setTickets(tickets);
        service.createOrder(ticketOrderParam);
    }

    @PostMapping("/update")
    public void updateOrder(@RequestBody UpdateTicketOrderRequest request) {
        List<Ticket> tickets = request.tickets().stream()
                .map(TicketControllerMapper.INSTANCE::requestToDomain)
                .toList();
        TicketOrderParam ticketOrderParam = TicketOrderControllerMapper.INSTANCE.requestToParam(request);
        ticketOrderParam.setTickets(tickets);
        service.updateOrder(ticketOrderParam);
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
        List<TicketOrderResult> ticketOrderResults = service.findAllOrders();
        List<TicketOrderResponse> ticketOrderResponses = ticketOrderResults.stream()
                .map(TicketOrderControllerMapper.INSTANCE::resultToResponse)
                .toList();
        for (int i = 0; i < ticketOrderResponses.size(); i++) {
            ticketOrderResponses.get(i).setTickets(
                    ticketOrderResults.get(i).tickets().stream()
                            .map(TicketControllerMapper.INSTANCE::domainToResponse)
                            .toList()
            );
        }
        return ResponseEntity.ok(ticketOrderResponses);
    }

    @GetMapping("/id/{orderId}")
    public ResponseEntity<TicketOrderResponse> orderById(@PathVariable UUID orderId) {
        TicketOrderResult ticketOrderResult = service.findOrderById(orderId);
        TicketOrderResponse ticketOrderResponse = TicketOrderControllerMapper.INSTANCE.resultToResponse(ticketOrderResult);
        ticketOrderResponse.setTickets(
                ticketOrderResult.tickets().stream()
                        .map(TicketControllerMapper.INSTANCE::domainToResponse)
                        .toList());
        return ResponseEntity.ok(ticketOrderResponse);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<TicketOrderResponse> orderByEmail(@PathVariable Email email) {
        TicketOrderResult ticketOrderResult = service.findOrderByEmail(email);
        TicketOrderResponse ticketOrderResponse = TicketOrderControllerMapper.INSTANCE.resultToResponse(ticketOrderResult);
        ticketOrderResponse.setTickets(
                ticketOrderResult.tickets().stream()
                        .map(TicketControllerMapper.INSTANCE::domainToResponse)
                        .toList());
        return ResponseEntity.ok(ticketOrderResponse);
    }

}
