package com.tangerine.theatre_manager.user.repository;

import com.tangerine.theatre_manager.user.model.GrantRequest;
import com.tangerine.theatre_manager.user.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrantRequestRepository extends JpaRepository<GrantRequest, Long> {

    Optional<GrantRequest> findByUser(User user);
}
