package com.tangerine.theatre_manager.user.service.dto;

import com.tangerine.theatre_manager.user.model.User;
import java.util.List;

public record UserResponse(
        String email,
        String ageRate,
        List<String> roles
) {

    public static UserResponse of(User user) {
        return new UserResponse(
                user.getEmail(),
                user.getAgeRateName(),
                user.getRoleNames()
        );
    }
}
