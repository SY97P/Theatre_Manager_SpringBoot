package com.tangerine.theatre_manager.global.auth;

import java.util.List;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtPrincipal {

    private String email;
    private String ageRange;
    private List<GrantedAuthority> roles;

    public JwtPrincipal() {
    }

    public JwtPrincipal(
            String email,
            String ageRange,
            List<GrantedAuthority> roles
    ) {
        this.email = email;
        this.ageRange = ageRange;
        this.roles = roles;
    }

    private JwtPrincipal getPrincipal() {
        return (JwtPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String email() {
        return email == null ? getPrincipal().email : email;
    }

    public String ageRange() {
        return ageRange == null ? getPrincipal().ageRange : ageRange;
    }

    public List<GrantedAuthority> roles() {
        return roles == null ? getPrincipal().roles : roles;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (JwtPrincipal) obj;
        return Objects.equals(this.email, that.email) &&
                Objects.equals(this.ageRange, that.ageRange) &&
                Objects.equals(this.roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, ageRange, roles);
    }

    @Override
    public String toString() {
        return "JwtPrincipal[" +
                "email=" + email + ", " +
                "ageRange=" + ageRange + ", " +
                "roles=" + roles + ']';
    }


}