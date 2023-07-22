package com.tangerine.theatre_manager.ticket.service.model;

import com.tangerine.theatre_manager.ticket.vo.Price;
import com.tangerine.theatre_manager.ticket.vo.Quantity;

import java.time.LocalDate;
import java.util.UUID;

public class Ticket {

    private UUID ticketId;
    private UUID orderId;
    private UUID performanceId;
    private Price ticketPrice;
    private Quantity ticketQuantity;
    private LocalDate reservedDate;

    public Ticket(UUID ticketId, UUID orderId, UUID performanceId, Price ticketPrice, Quantity ticketQuantity, LocalDate reservedDate) {
        this.ticketId = ticketId;
        this.orderId = orderId;
        this.performanceId = performanceId;
        this.ticketPrice = ticketPrice;
        this.ticketQuantity = ticketQuantity;
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

    public Quantity getTicketQuantity() {
        return ticketQuantity;
    }

    public void setTicketQuantity(Quantity ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    public LocalDate getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(LocalDate reservedDate) {
        this.reservedDate = reservedDate;
    }
}
