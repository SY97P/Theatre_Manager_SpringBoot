package com.tangerine.theatre_manager.user.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.tangerine.theatre_manager.global.auth.JwtPrincipal;
import com.tangerine.theatre_manager.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users", produces = APPLICATION_JSON_VALUE)
public class UserRestController {

    private final JwtPrincipal jwtPrincipal;
    private final UserService userService;

    public UserRestController(JwtPrincipal jwtPrincipal, UserService userService) {
        this.jwtPrincipal = jwtPrincipal;
        this.userService = userService;
    }

    @GetMapping(path = "/me")
    public String test() {
        return """
                {
                    "email": "%s",
                    "ageRange": "%s",
                    "roles": "%s"
                }
                """.formatted(jwtPrincipal.email(), jwtPrincipal.ageRange(), jwtPrincipal.roles());
    }

    @PatchMapping
    public ResponseEntity<UserResponse> updateUserCompanyGrant() {
        UserResponse response = userService.giveCompanyGrant(jwtPrincipal.email());
        return ResponseEntity
                .status(OK)
                .body(response);
    }

}
