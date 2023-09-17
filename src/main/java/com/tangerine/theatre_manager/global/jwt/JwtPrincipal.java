package com.tangerine.theatre_manager.global.jwt;

import java.util.StringJoiner;

public record JwtPrincipal(
        String email,
        String ageRange
) {

    @Override
    public String toString() {
        return new StringJoiner(", ", JwtPrincipal.class.getSimpleName() + "[", "]")
                .add("email='" + email + "'")
                .add("ageRange='" + ageRange + "'")
                .toString();
    }
}
