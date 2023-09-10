package com.tangerine.theatre_manager.global.user;

public record UserResponse(
    Long id,
    String token,
    String username,
    String provider,
    String providerId,
    String group,
    String profileImage
) {

  public static UserResponse of(String token, User user) {
    return new UserResponse(
        user.getId(),
        token,
        user.getUsername(),
        user.getProvider(),
        user.getProviderId(),
        user.getGroup().getName(),
        user.getProfileImage().orElse(null)
    );
  }
}
