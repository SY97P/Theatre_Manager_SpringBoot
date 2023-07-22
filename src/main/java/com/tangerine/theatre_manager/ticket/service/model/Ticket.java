package com.tangerine.theatre_manager.ticket.service.model;

import com.tangerine.theatre_manager.performance.vo.Price;

import java.time.LocalDate;
import java.util.UUID;

public class Ticket {

    private UUID ticketId;
    private UUID orderId;
    private UUID performanceId;
    private Price ticketPrice;
    private LocalDate reservedDate;

    public Ticket(UUID ticketId, UUID orderId, UUID performanceId, Price ticketPrice, LocalDate reservedDate) {
        this.ticketId = ticketId;
        this.orderId = orderId;
        this.performanceId = performanceId;
        this.ticketPrice = ticketPrice;
        this.reservedDate = reservedDate;
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getPerformanceId() {
        return performanceId;
    }

    public void setPerformanceId(UUID performanceId) {
        this.performanceId = performanceId;
    }

    public Price getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Price ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public LocalDate getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(LocalDate reservedDate) {
        this.reservedDate = reservedDate;
    }
}
