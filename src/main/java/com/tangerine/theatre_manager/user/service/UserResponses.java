package com.tangerine.theatre_manager.user.service;

import com.tangerine.theatre_manager.user.model.User;
import com.tangerine.theatre_manager.user.service.dto.UserResponse;
import org.springframework.data.domain.Page;

public record UserResponses(
        Page<UserResponse> responses
) {

    public static UserResponses of(Page<User> responses) {
        return new UserResponses(responses.map(UserResponse::of));
    }

}
