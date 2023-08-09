package com.tangerine.theatre_manager.performance.controller;

import com.tangerine.theatre_manager.global.response.SuccessCode;
import com.tangerine.theatre_manager.performance.controller.dto.CreatePerformanceRequest;
import com.tangerine.theatre_manager.performance.controller.dto.PerformanceResponse;
import com.tangerine.theatre_manager.performance.controller.dto.UpdatePerformanceRequest;
import com.tangerine.theatre_manager.performance.controller.mapper.PerformanceControllerMapper;
import com.tangerine.theatre_manager.performance.service.PerformanceService;
import com.tangerine.theatre_manager.performance.vo.PerformanceName;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/performances")
public class PerformanceRestController {

    private final PerformanceService service;
    private final PerformanceControllerMapper mapper;

    public PerformanceRestController(PerformanceService service, PerformanceControllerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerPerformance(@RequestBody CreatePerformanceRequest request) {
        UUID registeredId = service.createPerformance(mapper.requestToParam(request));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MessageFormat.format("{0} {1}", registeredId, SuccessCode.PERFORMANCE_SAVE_SUCCESS));
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePerformance(@RequestBody UpdatePerformanceRequest request) {
        UUID updatedId = service.updatePerformance(mapper.requestToParam(request));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MessageFormat.format("{0} {1}", updatedId, SuccessCode.PERFORMANCE_UPDATE_SUCCESS));
    }

    @DeleteMapping("/{performanceId}")
    public ResponseEntity<String> unregisterPerformanceById(@PathVariable UUID performanceId) {
        UUID deletedId = service.deletePerformanceById(performanceId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(MessageFormat.format("{0} {1}", deletedId, SuccessCode.PERFORMANCE_DELETE_SUCCESS));
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
