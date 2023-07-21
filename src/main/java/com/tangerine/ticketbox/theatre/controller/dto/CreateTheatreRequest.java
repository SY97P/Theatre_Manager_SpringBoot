package com.tangerine.ticketbox.theatre.controller.dto;

import java.time.LocalDate;

public record CreateTheatreRequest(
        String theatreName,
        String genre,
        String ageRate,
        LocalDate openRun,
        LocalDate closeRun,
        String stage
) {
}
