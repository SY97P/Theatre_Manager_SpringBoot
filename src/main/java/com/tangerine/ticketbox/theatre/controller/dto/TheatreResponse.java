package com.tangerine.ticketbox.theatre.controller.dto;

import com.tangerine.ticketbox.theatre.model.AgeRate;
import com.tangerine.ticketbox.theatre.model.Genre;
import com.tangerine.ticketbox.theatre.model.Stage;
import com.tangerine.ticketbox.theatre.model.TheatreName;

import java.time.LocalDate;
import java.util.UUID;

public record TheatreResponse(
        UUID theatreId,
        TheatreName theatreName,
        Genre genre,
        AgeRate ageRate,
        LocalDate openRun,
        LocalDate closeRun,
        Stage stage
) {
}
