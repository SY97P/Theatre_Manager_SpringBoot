package com.tangerine.ticketbox.theatre.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateTheatreRequest(
        UUID theatreId,
        String theatreName,
        String genre,
        String ageRate,
        LocalDate openRun,
        LocalDate closeRun,
        String stage
) {
}
