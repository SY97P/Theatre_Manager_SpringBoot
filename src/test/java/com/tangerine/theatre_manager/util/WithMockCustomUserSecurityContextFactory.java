package com.tangerine.theatre_manager.util;

import com.tangerine.theatre_manager.global.auth.JwtPrincipal;
import com.tangerine.theatre_manager.global.jwt.JwtAuthenticationToken;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements
        WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser mockCustomUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        JwtPrincipal jwtPrincipal = new JwtPrincipal(mockCustomUser.email(), mockCustomUser.ageRate(),
                List.of(new SimpleGrantedAuthority(mockCustomUser.role())));
        JwtAuthenticationToken authentication =
                new JwtAuthenticationToken(jwtPrincipal, null,
                        List.of(new SimpleGrantedAuthority(mockCustomUser.role())));
        context.setAuthentication(authentication);
        return context;
    }
}
