package com.tangerine.ticketbox.theatre.model;

import com.tangerine.ticketbox.global.exception.InvalidDataException;

public record TheatreName(String theatreNameValue) {

    public TheatreName {
        validate(theatreNameValue);
    }

    private void validate(String theatreNameValue) {
        if (theatreNameValue == null || theatreNameValue.isBlank()) {
            throw new InvalidDataException("Theatre name is invalid");
        }
    }
}
