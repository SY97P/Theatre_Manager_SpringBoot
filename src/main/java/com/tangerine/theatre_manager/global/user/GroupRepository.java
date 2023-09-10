package com.tangerine.theatre_manager.global.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

  Optional<Group> findByName(String name);

}
