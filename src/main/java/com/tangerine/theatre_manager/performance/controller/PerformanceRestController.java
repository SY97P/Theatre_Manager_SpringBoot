package com.tangerine.theatre_manager.performance.controller;

import com.tangerine.theatre_manager.performance.controller.dto.CreatePerformanceRequest;
import com.tangerine.theatre_manager.performance.controller.dto.PerformanceResponse;
import com.tangerine.theatre_manager.performance.controller.dto.UpdatePerformanceRequest;
import com.tangerine.theatre_manager.performance.controller.mapper.PerformanceControllerMapper;
import com.tangerine.theatre_manager.performance.service.PerformanceService;
import com.tangerine.theatre_manager.performance.vo.PerformanceName;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/performances")
public class PerformanceRestController {

    private final PerformanceService service;
    private final PerformanceControllerMapper mapper;

    public PerformanceRestController(PerformanceService service, PerformanceControllerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registerPerformance(@RequestBody CreatePerformanceRequest request) {
        service.createPerformance(mapper.requestToParam(request));
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updatePerformance(@RequestBody UpdatePerformanceRequest request) {
        service.updatePerformance(mapper.requestToParam(request));
    }

    @DeleteMapping("/{performanceId}")
    public void unregisterPerformanceById(@PathVariable UUID performanceId) {
        service.deletePerformanceById(performanceId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PerformanceResponse>> performanceList() {
        return ResponseEntity.ok(
                service.findAllPerformance()
                        .stream()
                        .map(mapper::resultToResponse)
                        .toList());
    }

    @GetMapping("/{performanceId}")
    public ResponseEntity<PerformanceResponse> performanceById(@PathVariable UUID performanceId) {
        return ResponseEntity.ok(mapper.resultToResponse(service.findPerformanceById(performanceId)));
    }

    @GetMapping("/name/{performanceName}")
    public ResponseEntity<PerformanceResponse> performanceByName(@PathVariable PerformanceName performanceName) {
        return ResponseEntity.ok(mapper.resultToResponse(service.findPerformanceByName(performanceName)));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<PerformanceResponse> performanceByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(mapper.resultToResponse(service.findPerformanceByDate(date)));
    }

}
