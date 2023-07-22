package com.tangerine.theatre_manager.performance.repository;

import com.tangerine.theatre_manager.performance.repository.model.Performance;
import com.tangerine.theatre_manager.performance.vo.PerformanceName;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PerformanceRepository {

    void insert(Performance performance);

    void update(Performance performance);

    void deleteAll();

    void deleteById(UUID performanceId);

    List<Performance> findAll();

    Performance findById(UUID performanceId);

    Performance findByName(PerformanceName performanceName);

    Performance findByDate(LocalDate date);

}
