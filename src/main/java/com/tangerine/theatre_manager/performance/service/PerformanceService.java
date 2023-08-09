package com.tangerine.theatre_manager.performance.service;

import com.tangerine.theatre_manager.performance.repository.PerformanceRepository;
import com.tangerine.theatre_manager.performance.service.dto.PerformanceParam;
import com.tangerine.theatre_manager.performance.service.dto.PerformanceResult;
import com.tangerine.theatre_manager.performance.service.mapper.PerformanceServiceMapper;
import com.tangerine.theatre_manager.performance.vo.PerformanceName;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PerformanceService {

    private final PerformanceRepository repository;
    private final PerformanceServiceMapper mapper;

    public PerformanceService(PerformanceRepository repository, PerformanceServiceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void createPerformance(PerformanceParam param) {
        repository.insert(mapper.paramToDomain(param));
    }

    public void updatePerformance(PerformanceParam param) {
        repository.update(mapper.paramToDomain(param));
    }

    public void deleteAllPerformance() {
        repository.deleteAll();
    }

    public void deletePerformanceById(UUID performanceId) {
        repository.deleteById(performanceId);
    }

    public List<PerformanceResult> findAllPerformance() {
        return repository.findAll()
                .stream()
                .map(mapper::domainToResult)
                .toList();
    }

    public PerformanceResult findPerformanceById(UUID performanceId) {
        return mapper.domainToResult(repository.findById(performanceId));
    }

    public PerformanceResult findPerformanceByName(PerformanceName performanceName) {
        return mapper.domainToResult(repository.findByName(performanceName));
    }

    public PerformanceResult findPerformanceByDate(LocalDate date) {
        return mapper.domainToResult(repository.findByDate(date));
    }

}
