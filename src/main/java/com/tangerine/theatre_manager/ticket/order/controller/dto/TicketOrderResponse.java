package com.tangerine.theatre_manager.ticket.order.controller.dto;

import com.tangerine.theatre_manager.ticket.controller.dto.TicketResponse;
import com.tangerine.theatre_manager.ticket.order.vo.Email;
import com.tangerine.theatre_manager.ticket.order.vo.TicketOrderStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class TicketOrderResponse {
    private final UUID orderId;
    private final Email email;
    private final LocalDate orderedAt;
    private final TicketOrderStatus ticketOrderStatus;
    private List<TicketResponse> tickets;

    public TicketOrderResponse(
            UUID orderId,
            Email email,
            LocalDate orderedAt,
            TicketOrderStatus ticketOrderStatus,
            List<TicketResponse> tickets
    ) {
        this.orderId = orderId;
        this.email = email;
        this.orderedAt = orderedAt;
        this.ticketOrderStatus = ticketOrderStatus;
        this.tickets = tickets;
    }

    public UUID orderId() {
        return orderId;
    }

    public Email email() {
        return email;
    }

    public LocalDate orderedAt() {
        return orderedAt;
    }

    public TicketOrderStatus ticketOrderStatus() {
        return ticketOrderStatus;
    }

    public List<TicketResponse> tickets() {
        return tickets;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TicketOrderResponse) obj;
        return Objects.equals(this.orderId, that.orderId) &&
                Objects.equals(this.email, that.email) &&
                Objects.equals(this.orderedAt, that.orderedAt) &&
                Objects.equals(this.ticketOrderStatus, that.ticketOrderStatus) &&
                Objects.equals(this.tickets, that.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, email, orderedAt, ticketOrderStatus, tickets);
    }

    @Override
    public String toString() {
        return "TicketOrderResponse[" +
                "orderId=" + orderId + ", " +
                "email=" + email + ", " +
                "orderedAt=" + orderedAt + ", " +
                "ticketOrderStatus=" + ticketOrderStatus + ", " +
                "tickets=" + tickets + ']';
    }

    public void setTickets(List<TicketResponse> tickets) {
        this.tickets = tickets;
    }

}
