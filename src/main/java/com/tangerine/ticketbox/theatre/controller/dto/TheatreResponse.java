package com.tangerine.ticketbox.theatre.controller.dto;

import com.tangerine.ticketbox.theatre.vo.AgeRate;
import com.tangerine.ticketbox.theatre.vo.Genre;
import com.tangerine.ticketbox.theatre.vo.Stage;

import java.time.LocalDate;
import java.util.UUID;

public record TheatreResponse(
        UUID theatreId,
        String theatreName,
        Genre genre,
        AgeRate ageRate,
        LocalDate openRun,
        LocalDate closeRun,
        Stage stage
) {
}
