package com.tangerine.theatre_manager.user.repository;

import com.tangerine.theatre_manager.user.model.GrantRequest;
import com.tangerine.theatre_manager.user.model.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GrantRequestRepository extends JpaRepository<GrantRequest, Long> {

    Optional<GrantRequest> findByUser(User user);

    @Query("select g.user from GrantRequest g where g.granted = false")
    Page<User> findUserNotGranted(Pageable pageable);
}
