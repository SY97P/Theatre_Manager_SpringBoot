package com.tangerine.theatre_manager.performance.service;

import com.tangerine.theatre_manager.global.exception.PerformanceException;
import com.tangerine.theatre_manager.performance.model.Performance;
import com.tangerine.theatre_manager.performance.repository.PerformanceRepository;
import com.tangerine.theatre_manager.performance.service.dto.PerformanceParam;
import com.tangerine.theatre_manager.performance.service.dto.PerformanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PerformanceService {

    private final PerformanceRepository performanceRepository;

    public PerformanceService(PerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

    @Transactional
    public PerformanceResponse registerPerformance(PerformanceParam param) {
        Performance performance = performanceRepository.save(PerformanceParam.to(param));
        return PerformanceResponse.of(performance);
    }

    @Transactional
    public PerformanceResponse editPerformance(
            Long performanceId, PerformanceParam param
    ) {
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new PerformanceException());
        Performance updated = performance.update(PerformanceParam.to(param));
        return PerformanceResponse.of(updated);
    }

    @Transactional
    public void removePerformance(Long performanceId) {
        performanceRepository.deleteById(performanceId);
    }

    public PerformanceResponse findPerformance(Long performanceId) {
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new PerformanceException());
        return PerformanceResponse.of(performance);
    }

    public PerformanceResponses findPerformanceByTitle(String title, Pageable pageable) {
        Page<Performance> performances = performanceRepository.findByTitle(title, pageable);
        return PerformanceResponses.of(performances);
    }

    public PerformanceResponses findAllPerformances(Pageable pageable) {
        Page<Performance> performances = performanceRepository.findAll(pageable);
        return PerformanceResponses.of(performances);
    }
}
