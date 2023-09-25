package com.tangerine.theatre_manager.user.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.tangerine.theatre_manager.global.auth.Email;
import com.tangerine.theatre_manager.global.auth.JwtPrincipal;
import com.tangerine.theatre_manager.user.service.UserResponses;
import com.tangerine.theatre_manager.user.service.UserService;
import com.tangerine.theatre_manager.user.service.dto.UserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
                    "ageRate": "%s",
                    "roles": "%s"
                }
                """.formatted(jwtPrincipal.email(), jwtPrincipal.ageRate(), jwtPrincipal.roles());
    }

    @PatchMapping(path = "/company/{email}")
    public ResponseEntity<UserResponse> updateUserCompanyGrant(
            @PathVariable String email
    ) {
        UserResponse response = userService.giveCompanyGrant(new Email(email));
        return ResponseEntity
                .status(OK)
                .body(response);
    }

    @PostMapping(path = "/company")
    public ResponseEntity<String> requestUserCompanyGrant() {
        userService.requestCompanyGrant(new Email(jwtPrincipal.email()));
        return ResponseEntity
                .status(OK)
                .body("요청이 성공적으로 접수되었습니다.");
    }

    @GetMapping(path = "/company")
    public ResponseEntity<UserResponses> readAllCompanyGrantRequest(
            Pageable pageable
    ) {
        UserResponses responses = userService.findAllCompanyGrantRequest(pageable);
        return ResponseEntity
                .status(OK)
                .body(responses);
    }

}
