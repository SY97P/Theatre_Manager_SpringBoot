package com.tangerine.theatre_manager.performance.repository;

import com.tangerine.theatre_manager.performance.model.Performance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    Page<Performance> findByTitle(String title, Pageable pageable);

}
