package com.tangerine.ticketbox.theatre.model;

import java.time.LocalDate;
import java.util.UUID;


public record Theatre(
        UUID theatreId,
        TheatreName theatreName,
        Genre genre,
        AgeRate ageRate,
        LocalDate openRun,
        LocalDate closeRun,
        Stage stage
) {

}
