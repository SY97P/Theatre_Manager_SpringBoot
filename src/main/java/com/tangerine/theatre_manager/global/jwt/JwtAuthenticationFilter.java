package com.tangerine.theatre_manager.global.jwt;

import static io.micrometer.common.util.StringUtils.isNotEmpty;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

public class JwtAuthenticationFilter extends GenericFilterBean {

  private final Logger log = LoggerFactory.getLogger(getClass());

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
        try {
          Claims claims = verify(token);
          log.debug("JwtAuthenticationProvider parse result: {}", claims);

          String username = claims.getUsername();
          List<GrantedAuthority> authorities = getAuthorities(claims);

          if (isNotEmpty(username) && !authorities.isEmpty()) {
            JwtAuthenticationToken authentication =
                new JwtAuthenticationToken(
                    new JwtAuthentication(token, username), null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
          }
        } catch (Exception e) {
          log.warn("JwtAuthenticationProvider processing failed: {}", e.getMessage());
        }
      }
    } else {
      log.debug(
          "SecurityContextHolder not populated with security token, as it already contained: '{}'",
          SecurityContextHolder.getContext().getAuthentication());
    }

    chain.doFilter(request, response);
  }

  private String getToken(HttpServletRequest request) {
    String token = request.getHeader(jwtAuthenticationProvider.getHeader());
    if (isNotEmpty(token)) {
      log.debug("JwtAuthenticationProvider authorization api detected: {}", token);
      return URLDecoder.decode(token, StandardCharsets.UTF_8);
    }
    return null;
  }

  private Claims verify(String token) {
    return jwtAuthenticationProvider.verify(token);
  }

  private List<GrantedAuthority> getAuthorities(Claims claims) {
    String[] roles = claims.getRoles();
    boolean condition = (roles == null || roles.length == 0);
    return condition
        ? emptyList()
        : Arrays.stream(roles)
            .map(SimpleGrantedAuthority::new)
            .collect(toList());
  }

}