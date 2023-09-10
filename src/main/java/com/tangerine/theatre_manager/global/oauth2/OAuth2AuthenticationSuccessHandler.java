package com.tangerine.theatre_manager.global.oauth2;

import com.tangerine.theatre_manager.global.jwt.JwtAuthenticationProvider;
import com.tangerine.theatre_manager.global.user.User;
import com.tangerine.theatre_manager.global.user.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * OAuth2 인증이 완료됐을때 후처리 담당할 AuthenticationSuccessHandler 인터페이스 구현체
 */
public class OAuth2AuthenticationSuccessHandler extends
    SavedRequestAwareAuthenticationSuccessHandler {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final JwtAuthenticationProvider jwt;

  private final UserService userService;

  public OAuth2AuthenticationSuccessHandler(JwtAuthenticationProvider jwt,
      UserService userService) {
    this.jwt = jwt;
    this.userService = userService;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws ServletException, IOException {
    if (authentication instanceof OAuth2AuthenticationToken) {
      OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
      OAuth2User principal = oauth2Token.getPrincipal();
      String registrationId = oauth2Token.getAuthorizedClientRegistrationId();

      User user = processUserOAuth2UserJoin(principal, registrationId);
      String loginSuccessJson = generateLoginSuccessJson(user);
      response.setContentType("application/json;charset=UTF-8");
      response.setContentLength(loginSuccessJson.getBytes(StandardCharsets.UTF_8).length);
      response.getWriter().write(loginSuccessJson);
    } else {
      super.onAuthenticationSuccess(request, response, authentication);
    }
  }

  // OAuth2 인증이 완료된 사용자가 신규 사용자면 사용자 가입시킴
  private User processUserOAuth2UserJoin(OAuth2User oAuth2User, String registrationId) {
    return userService.join(oAuth2User, registrationId);
  }

  // 서비스 접근을 위한 JWT 토큰 json 응답
  private String generateLoginSuccessJson(User user) {
    String token = generateToken(user);
    log.debug("Jwt({}) created for oauth2 login user {}", token, user.getUsername());
    return "{\"token\":\"" + token + "\", \"username\":\"" + user.getUsername() + "\", \"group\":\""
        + user.getGroup().getName() + "\"}";
  }

  // 서비스 접근을 위한 JWT 토큰 생성
  private String generateToken(User user) {
    return jwt.sign(JwtAuthenticationProvider.Claims.from(user.getUsername(), new String[]{"ROLE_USER"}));
  }

}
