package com.tangerine.ticketbox.ticket;

import java.time.LocalDate;
import java.util.UUID;

public class Ticket {

    private final UUID ticketd;
    private final UUID theatreId;
    private final Price ticketPrice;
    private final Quantity ticketQuantity;
    private final LocalDate reservedDate;

    public Ticket(UUID ticketd, UUID theatreId, Price ticketPrice, Quantity ticketQuantity, LocalDate reservedDate) {
        this.ticketd = ticketd;
        this.theatreId = theatreId;
        this.ticketPrice = ticketPrice;
        this.ticketQuantity = ticketQuantity;
        this.reservedDate = reservedDate;
    }
}
