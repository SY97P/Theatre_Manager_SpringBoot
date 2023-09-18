package com.tangerine.theatre_manager.global.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Arrays;
import java.util.Date;
import java.util.StringJoiner;
import org.springframework.util.Assert;

public class Claims {

    private String email;
    private String ageRange;
    private String[] roles;
    private Date issuedAt;
    private Date expiresAt;

    Claims(DecodedJWT decodedJWT) {
        this.email = getEmailFromClaim(decodedJWT);
        this.ageRange = getAgeRangeFromClaim(decodedJWT);
        this.roles = getRolesFromClaim(decodedJWT);
        this.issuedAt = decodedJWT.getIssuedAt();
        this.expiresAt = decodedJWT.getExpiresAt();
    }

    private Claims() {
    }

    public static Claims from(String email, String ageRange, String[] roles) {
        Claims claims = new Claims();
        claims.email = email;
        claims.ageRange = ageRange;
        claims.roles = roles;
        return claims;
    }

    private String getEmailFromClaim(DecodedJWT decodedJWT) {
        Claim email = decodedJWT.getClaim("email");
        Assert.notNull(email, "email is not in claim");
        return email.asString();
    }

    private String getAgeRangeFromClaim(DecodedJWT decodedJWT) {
        Claim ageRange = decodedJWT.getClaim("ageRange");
        Assert.notNull(ageRange, "roles is not in claim");
        return ageRange.asString();
    }

    private String[] getRolesFromClaim(DecodedJWT decodedJWT) {
        Claim roles = decodedJWT.getClaim("roles");
        Assert.notNull(roles, "roles is not in claim");
        return roles.asArray(String.class);
    }

    public String getEmail() {
        return email;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public String[] getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Claims.class.getSimpleName() + "[", "]")
                .add("email='" + email + "'")
                .add("ageRange='" + ageRange + "'")
                .add("roles=" + Arrays.toString(roles))
                .add("issuedAt=" + issuedAt)
                .add("expiresAt=" + expiresAt)
                .toString();
    }
}
