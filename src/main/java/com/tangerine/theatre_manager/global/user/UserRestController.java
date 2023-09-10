package com.tangerine.theatre_manager.global.user;

import static org.springframework.http.HttpStatus.OK;

import com.tangerine.theatre_manager.global.jwt.JwtAuthentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserRestController {

  private final UserService service;

  public UserRestController(UserService service) {
    this.service = service;
  }

  @GetMapping(path = "/user/me")
  public ResponseEntity<UserResponse> me(
      @AuthenticationPrincipal JwtAuthentication authentication) {
    UserResponse userResponse = service.findUserFromAuthentication(authentication);
    return ResponseEntity
        .status(OK)
        .body(userResponse);
  }

}