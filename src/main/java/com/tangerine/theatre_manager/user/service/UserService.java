package com.tangerine.theatre_manager.user.service;

import static com.tangerine.theatre_manager.global.exception.ErrorCode.NOT_FOUND_USER;
import static com.tangerine.theatre_manager.global.exception.ErrorCode.NOT_REQUEST_USER;

import com.tangerine.theatre_manager.global.auth.Email;
import com.tangerine.theatre_manager.global.auth.JwtPrincipal;
import com.tangerine.theatre_manager.global.auth.Role;
import com.tangerine.theatre_manager.global.exception.GrantRequestException;
import com.tangerine.theatre_manager.global.exception.UserException;
import com.tangerine.theatre_manager.performance.model.vo.AgeRate;
import com.tangerine.theatre_manager.user.model.GrantRequest;
import com.tangerine.theatre_manager.user.model.User;
import com.tangerine.theatre_manager.user.model.UserDetailsImpl;
import com.tangerine.theatre_manager.user.repository.GrantRequestRepository;
import com.tangerine.theatre_manager.user.repository.UserRepository;
import com.tangerine.theatre_manager.user.service.dto.UserResponse;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;
    private final GrantRequestRepository grantRequestRepository;

    public UserService(UserRepository userRepository, GrantRequestRepository grantRequestRepository) {
        this.userRepository = userRepository;
        this.grantRequestRepository = grantRequestRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userEntity = this.findByEmail(email)
                .map(user -> {
                    log.warn("Already exists user: {}", user);
                    return user;
                })
                .orElseGet(() -> userRepository.save(
                        new User(email,
                                AgeRate.KID_AVAILABLE,
                                List.of(Role.USER))));
        return new UserDetailsImpl(userEntity);
    }

    private Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void bindUserAgeRate(JwtPrincipal principal) {
        this.getUser(new Email(principal.email()))
                .setAgeRate(AgeRate.valueOf(principal.ageRange()));
    }

    @Transactional
    public UserResponse giveCompanyGrant(Email email) {
        User user = getUser(email).addRole(Role.COMPANY);
        grantRequestRepository.findByUser(user)
                .orElseThrow(() -> new GrantRequestException(NOT_REQUEST_USER))
                .giveGrant();
        return UserResponse.of(user);
    }

    public void requestCompanyGrant(Email email) {
        User user = getUser(email);
        grantRequestRepository.findByUser(user)
                .orElse(grantRequestRepository.save(
                        new GrantRequest(false, user)));
    }

    private User getUser(Email email) {
        return this.findByEmail(email.getAddress())
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));
    }

    public UserResponses findAllCompanyGrantRequest(Pageable pageable) {
        Page<User> userNotGranted = grantRequestRepository.findUserNotGranted(pageable);
        return UserResponses.of(userNotGranted);
    }
}
