package com.tangerine.theatre_manager.global.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.StringJoiner;
import org.springframework.util.Assert;

public class Claims {

    private String email;
    private String ageRange;
    private Date issuedAt;
    private Date expiresAt;

    private Claims() {
    }

    Claims(DecodedJWT decodedJWT) {
        this.email = getEmailFromClaim(decodedJWT);
        this.ageRange = getRoleFromClaim(decodedJWT);
        this.issuedAt = decodedJWT.getIssuedAt();
        this.expiresAt = decodedJWT.getExpiresAt();
    }

    public static Claims from(String email, String role) {
        Claims claims = new Claims();
        claims.email = email;
        claims.ageRange = role;
        return claims;
    }

    private String getEmailFromClaim(DecodedJWT decodedJWT) {
        Claim email = decodedJWT.getClaim("email");
        Assert.notNull(email, "email is not in claim");
        return email.asString();
    }

    private String getRoleFromClaim(DecodedJWT decodedJWT) {
        Claim role = decodedJWT.getClaim("role");
        Assert.notNull(role, "roles is not in claim");
        return role.asString();
    }

    public String getEmail() {
        return email;
    }

    public String getAgeRange() {
        return ageRange;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Claims.class.getSimpleName() + "[", "]")
                .add("email='" + email + "'")
                .add("role='" + ageRange + "'")
                .add("issuedAt=" + issuedAt)
                .add("expiresAt=" + expiresAt)
                .toString();
    }
}
