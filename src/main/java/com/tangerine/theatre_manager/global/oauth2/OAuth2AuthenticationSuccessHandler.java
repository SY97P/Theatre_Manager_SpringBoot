package com.tangerine.theatre_manager.global.oauth2;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.tangerine.theatre_manager.global.auth.JwtPrincipal;
import com.tangerine.theatre_manager.global.jwt.Claims;
import com.tangerine.theatre_manager.global.jwt.JwtAuthenticationProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.util.Assert;

public class OAuth2AuthenticationSuccessHandler extends
        SavedRequestAwareAuthenticationSuccessHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final JwtAuthenticationProvider jwtProvider;

    public OAuth2AuthenticationSuccessHandler(JwtAuthenticationProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {
            OAuth2User principal = oauth2Token.getPrincipal();

            JwtPrincipal jwtPrincipal = getUserInfo(principal);

            String loginSuccessJson = generateLoginSuccessJson(jwtPrincipal);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(UTF_8.name());
            response.setContentLength(loginSuccessJson.getBytes(UTF_8).length);
            response.getWriter().write(loginSuccessJson);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    private JwtPrincipal getUserInfo(OAuth2User oAuth2User) {
        Assert.isTrue(oAuth2User != null, "oauth2User must be provided");

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

        Assert.isTrue(account != null, "OAuth2User account is empty");

        String email = (String) account.get("email");
        String ageRange = (String) account.get("age_range");
        List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority("USER"));

        return new JwtPrincipal(email, ageRange, roles);
    }

    private String generateLoginSuccessJson(JwtPrincipal jwtPrincipal) {
        String token = generateToken(jwtPrincipal);
        log.debug("Jwt({}) created for oauth2 login user", token);
        return """
                {
                  "token": "%s",
                  "email": "%s",
                  "ageRange": "%s"
                }
                """.formatted(token, jwtPrincipal.email(), jwtPrincipal.ageRange());
    }

    private String generateToken(JwtPrincipal jwtPrincipal) {
        return jwtProvider.sign(
                Claims.from(
                        jwtPrincipal.email(),
                        jwtPrincipal.ageRange(),
                        jwtPrincipal.roles().stream()
                                .map(GrantedAuthority::getAuthority)
                                .toArray(String[]::new)));
    }

}
