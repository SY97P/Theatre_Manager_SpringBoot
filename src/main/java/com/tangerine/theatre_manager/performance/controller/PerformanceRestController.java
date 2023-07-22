package com.tangerine.theatre_manager.performance.controller;

import com.tangerine.theatre_manager.performance.controller.dto.CreatePerformanceRequest;
import com.tangerine.theatre_manager.performance.controller.dto.PerformanceResponse;
import com.tangerine.theatre_manager.performance.controller.dto.UpdatePerformanceRequest;
import com.tangerine.theatre_manager.performance.controller.mapper.PerformanceControllerMapper;
import com.tangerine.theatre_manager.performance.service.PerformanceService;
import com.tangerine.theatre_manager.performance.vo.PerformanceName;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/performances")
public class PerformanceRestController {

    private final PerformanceService service;

    public PerformanceRestController(PerformanceService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public void registerPerformance(@RequestBody CreatePerformanceRequest request) {
        service.createPerformance(PerformanceControllerMapper.INSTANCE.requestToParam(request));
    }

    @GetMapping("/update")
    public void updatePerformance(@RequestBody UpdatePerformanceRequest request) {
        service.updatePerformance(PerformanceControllerMapper.INSTANCE.requestToParam(request));
    }

    @GetMapping("/unregister/all")
    public void unregisterPerformances() {
        service.deleteAllPerformance();
    }

    @GetMapping("/unregister/{performanceId}")
    public void unregisterPerformanceById(@PathVariable UUID performanceId) {
        service.deletePerformanceById(performanceId);
    }

    @GetMapping("")
    public ResponseEntity<List<PerformanceResponse>> performanceList() {
        return ResponseEntity.ok(
                service.findAllPerformance()
                        .stream()
                        .map(PerformanceControllerMapper.INSTANCE::resultToResponse)
                        .toList());
    }

    @GetMapping("/id/{performanceId}")
    public ResponseEntity<PerformanceResponse> performanceById(@PathVariable UUID performanceId) {
        return ResponseEntity.ok(PerformanceControllerMapper.INSTANCE.resultToResponse(service.findPerformanceById(performanceId)));
    }

    @GetMapping("/name/{performanceName}")
    public ResponseEntity<PerformanceResponse> performanceByName(@PathVariable PerformanceName performanceName) {
        return ResponseEntity.ok(PerformanceControllerMapper.INSTANCE.resultToResponse(service.findPerformanceByName(performanceName)));
    }

    // todo -> 500 internet server error
    @GetMapping("/date/{date}")
    public ResponseEntity<PerformanceResponse> performanceByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(PerformanceControllerMapper.INSTANCE.resultToResponse(service.findPerformanceByDate(date)));
    }
}
