package com.tangerine.theatre_manager.global.aop.aspect;

import static com.tangerine.theatre_manager.global.exception.ErrorCode.FORBIDDEN_AUTH;

import com.tangerine.theatre_manager.global.auth.JwtPrincipal;
import com.tangerine.theatre_manager.global.auth.Role;
import com.tangerine.theatre_manager.global.exception.AuthorizedException;
import java.util.Objects;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FilterCompanyAspect {

    private final JwtPrincipal jwtPrincipal;

    public FilterCompanyAspect(JwtPrincipal jwtPrincipal) {
        this.jwtPrincipal = jwtPrincipal;
    }

    @Pointcut("@annotation(com.tangerine.theatre_manager.global.aop.annotations.FilterCompany)")
    private void enableFilter() {
    }

    @Before("enableFilter()")
    public void before() {
        boolean isCompany = jwtPrincipal.roles().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> Objects.equals(authority, Role.COMPANY.name()));
        if (!isCompany) {
            throw new AuthorizedException(FORBIDDEN_AUTH);
        }
    }

}
