package com.tangerine.theatre_manager.global.jwt;

import static io.micrometer.common.util.StringUtils.isNotEmpty;

import com.tangerine.theatre_manager.global.auth.Email;
import com.tangerine.theatre_manager.global.auth.JwtPrincipal;
import com.tangerine.theatre_manager.performance.model.vo.AgeRate;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public JwtAuthenticationFilter(JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String token = getToken(request);
            if (token != null) {
                setSecurityContext(token, request);
            }
        }

        chain.doFilter(request, response);
    }

    private void setSecurityContext(String token, HttpServletRequest request) {
        Claims claims = verify(token);

        Email email = new Email(claims.getEmail());
        AgeRate ageRate = AgeRate.valueOf(claims.getAgeRate());
        List<GrantedAuthority> authorities = getAuthorities(claims.getRoles());
        JwtPrincipal principal = new JwtPrincipal(email.getAddress(), ageRate.name(), authorities);

        if (!authorities.isEmpty()) {
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(principal, null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(jwtAuthenticationProvider.getHeader());

        if (isNotEmpty(token)) {
            return URLDecoder.decode(token.substring(7), StandardCharsets.UTF_8);
        }
        return null;
    }

    private Claims verify(String token) {
        return jwtAuthenticationProvider.verify(token);
    }

    private List<GrantedAuthority> getAuthorities(String[] roles) {
        return roles == null || roles.length == 0
                ? List.of()
                : Arrays.stream(roles)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
    }

}