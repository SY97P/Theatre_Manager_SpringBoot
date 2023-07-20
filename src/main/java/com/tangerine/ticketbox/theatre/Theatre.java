package com.tangerine.ticketbox.theatre;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Theatre {

    private final UUID theatreId;
    private final TheatreName theatreName;
    private final Genre genre;
    private final AgeRate ageRate;
    private final LocalDate openRun;
    private final LocalDate closeRun;
    private final Stage stage;
    private final List<Actor> actors;

    public Theatre(UUID theatreId, TheatreName theatreName, Genre genre, AgeRate ageRate, LocalDate openRun, LocalDate closeRun, Stage stage, List<Actor> actors) {
        this.theatreId = theatreId;
        this.theatreName = theatreName;
        this.genre = genre;
        this.ageRate = ageRate;
        this.openRun = openRun;
        this.closeRun = closeRun;
        this.stage = stage;
        this.actors = actors;
    }
}
