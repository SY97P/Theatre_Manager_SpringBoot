package com.tangerine.theatre_manager.global.user;

import static io.micrometer.common.util.StringUtils.isNotEmpty;

import com.tangerine.theatre_manager.global.jwt.JwtAuthentication;
import jakarta.persistence.EntityNotFoundException;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(readOnly = true)
public class UserService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final UserRepository userRepository;
  private final GroupRepository groupRepository;

  public UserService(UserRepository userRepository, GroupRepository groupRepository) {
    this.userRepository = userRepository;
    this.groupRepository = groupRepository;
  }

  @Transactional(readOnly = true)
  public UserResponse findUserFromAuthentication(JwtAuthentication authentication) {
    Assert.notNull(authentication, "username must be provided");
    User user = userRepository.findByUsername(authentication.username)
        .orElseThrow(EntityNotFoundException::new);
    return UserResponse.of(authentication.token, user);
  }

  @Transactional(readOnly = true)
  public Optional<User> findByProviderAndProviderId(String provider, String providerId) {
    Assert.isTrue(isNotEmpty(provider), "provider must be provided");
    Assert.isTrue(isNotEmpty(providerId), "providerId must be provided");

    return userRepository.findByProviderAndProviderId(provider, providerId);
  }

  @Transactional
  public User join(OAuth2User oAuth2User, String provider) {
    Assert.isTrue(oAuth2User != null, "oauth2User must be provided");
    Assert.isTrue(isNotEmpty(provider), "provider must be provided");

        /*
          username - 카카오 닉네임
          provider - 파라미터
          providerId - oauth2User.getName()
          profileImage - 카카오 인증된 사용자의 프로필 이미지 사용
          group - USER_GROUP Group
         */
    String providerId = oAuth2User.getName();
    return findByProviderAndProviderId(provider, providerId)
        .map(user -> {
          log.warn("Already exists: {} for provider: {} providerId: {}", user, provider,
              providerId);
          return user;
        })
        .orElseGet(() -> {
          Map<String, Object> attributes = oAuth2User.getAttributes();
          Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
          Assert.isTrue(properties != null, "OAuth2User properties is empty");

          String nickname = (String) properties.get("nickname");
          String profileImage = (String) properties.get("profile_image");
          Group group = groupRepository.findByName("USER_GROUP")
              .orElseThrow(
                  () -> new IllegalArgumentException("Could not found group for USER_GROUP"));
          return userRepository.save(
              new User(nickname, provider, providerId, profileImage, group)
          );
        });
  }

}
