package com.tangerine.ticketbox.ticket.order.service.model;

import com.tangerine.ticketbox.ticket.order.vo.TicketOrderStatus;
import com.tangerine.ticketbox.ticket.service.model.Ticket;
import com.tangerine.ticketbox.ticket.order.vo.Email;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TicketOrder {

    private UUID orderId;
    private Email email;
    private LocalDate orderedAt;
    private TicketOrderStatus ticketOrderStatus;
    private List<Ticket> tickets;

    public TicketOrder(UUID orderId, Email email, LocalDate orderedAt, TicketOrderStatus ticketOrderStatus, List<Ticket> tickets) {
        this.orderId = orderId;
        this.email = email;
        this.orderedAt = orderedAt;
        this.ticketOrderStatus = ticketOrderStatus;
        this.tickets = tickets;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Email getEmail() {
        return email;
    }

    public LocalDate getOrderedAt() {
        return orderedAt;
    }

    public TicketOrderStatus getTicketOrderStatus() {
        return ticketOrderStatus;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setOrderedAt(LocalDate orderedAt) {
        this.orderedAt = orderedAt;
    }

    public void setTicketOrderStatus(TicketOrderStatus ticketOrderStatus) {
        this.ticketOrderStatus = ticketOrderStatus;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
