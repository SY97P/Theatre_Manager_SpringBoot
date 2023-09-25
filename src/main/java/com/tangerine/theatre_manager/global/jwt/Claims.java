package com.tangerine.theatre_manager.global.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Arrays;
import java.util.Date;
import java.util.StringJoiner;
import org.springframework.util.Assert;

public class Claims {

    private String email;
    private String ageRate;
    private String[] roles;
    private Date issuedAt;
    private Date expiresAt;

    Claims(DecodedJWT decodedJWT) {
        this.email = getEmailFromClaim(decodedJWT);
        this.ageRate = getAgeRateFromClaim(decodedJWT);
        this.roles = getRolesFromClaim(decodedJWT);
        this.issuedAt = decodedJWT.getIssuedAt();
        this.expiresAt = decodedJWT.getExpiresAt();
    }

    private Claims() {
    }

    public static Claims from(String email, String ageRate, String[] roles) {
        Claims claims = new Claims();
        claims.email = email;
        claims.ageRate = ageRate;
        claims.roles = roles;
        return claims;
    }

    private String getEmailFromClaim(DecodedJWT decodedJWT) {
        Claim email = decodedJWT.getClaim("email");
        Assert.notNull(email, "email is not in claim");
        return email.asString();
    }

    private String getAgeRateFromClaim(DecodedJWT decodedJWT) {
        Claim ageRate = decodedJWT.getClaim("ageRate");
        Assert.notNull(ageRate, "ageRate is not in claim");
        return ageRate.asString();
    }

    private String[] getRolesFromClaim(DecodedJWT decodedJWT) {
        Claim roles = decodedJWT.getClaim("roles");
        Assert.notNull(roles, "roles is not in claim");
        return roles.asArray(String.class);
    }

    public String getEmail() {
        return email;
    }

    public String getAgeRate() {
        return ageRate;
    }

    public String[] getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Claims.class.getSimpleName() + "[", "]")
                .add("email='" + email + "'")
                .add("ageRate='" + ageRate + "'")
                .add("roles=" + Arrays.toString(roles))
                .add("issuedAt=" + issuedAt)
                .add("expiresAt=" + expiresAt)
                .toString();
    }
}
