package com.tangerine.theatre_manager.user.service;

import com.tangerine.theatre_manager.global.auth.JwtPrincipal;
import com.tangerine.theatre_manager.global.auth.Role;
import com.tangerine.theatre_manager.performance.model.vo.AgeRate;
import com.tangerine.theatre_manager.user.controller.UserResponse;
import com.tangerine.theatre_manager.user.model.User;
import com.tangerine.theatre_manager.user.model.UserDetailsImpl;
import com.tangerine.theatre_manager.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final JwtPrincipal principal;

    public UserService(UserRepository userRepository, JwtPrincipal principal) {
        this.userRepository = userRepository;
        this.principal = principal;
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
        this.findByEmail(principal.email()).get()
                .setAgeRate(AgeRate.valueOf(principal.ageRange()));
    }

    @Transactional
    public UserResponse giveCompanyGrant(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + " 유저는 존재하지 않습니다."))
                .addRole(Role.COMPANY);
        return UserResponse.of(user);
    }
}
