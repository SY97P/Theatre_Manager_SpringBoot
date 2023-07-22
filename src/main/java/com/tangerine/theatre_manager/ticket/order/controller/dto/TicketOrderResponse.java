package com.tangerine.theatre_manager.ticket.order.controller.dto;

import com.tangerine.theatre_manager.ticket.controller.dto.TicketResponse;
import com.tangerine.theatre_manager.ticket.order.vo.Email;
import com.tangerine.theatre_manager.ticket.order.vo.TicketOrderStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class TicketOrderResponse {
    private UUID orderId;
    private String email;
    private LocalDate orderedAt;
    private TicketOrderStatus ticketOrderStatus;
    private List<TicketResponse> tickets;

    public TicketOrderResponse(UUID orderId, String email, LocalDate orderedAt, TicketOrderStatus ticketOrderStatus, List<TicketResponse> tickets) {
        this.orderId = orderId;
        this.email = email;
        this.orderedAt = orderedAt;
        this.ticketOrderStatus = ticketOrderStatus;
        this.tickets = tickets;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(LocalDate orderedAt) {
        this.orderedAt = orderedAt;
    }

    public TicketOrderStatus getTicketOrderStatus() {
        return ticketOrderStatus;
    }

    public void setTicketOrderStatus(TicketOrderStatus ticketOrderStatus) {
        this.ticketOrderStatus = ticketOrderStatus;
    }

    public List<TicketResponse> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketResponse> tickets) {
        this.tickets = tickets;
    }
}
