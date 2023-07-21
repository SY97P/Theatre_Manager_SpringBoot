package com.tangerine.ticketbox.order.model;

import com.tangerine.ticketbox.global.exception.InvalidDataException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Email(String emailAddress) {

    public Email {
        validateEmail(emailAddress);
    }

    private void validateEmail(String emailAddress) {
        String regExp = "\\b[\\w.-]+@[\\w.-]+\\.\\w{2,4}\\b";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.matches()) {
            throw new InvalidDataException("Email is not valid");
        }
    }
}
