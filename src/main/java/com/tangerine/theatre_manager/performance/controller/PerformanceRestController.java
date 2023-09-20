package com.tangerine.theatre_manager.performance.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.tangerine.theatre_manager.performance.controller.dto.PerformanceRequest;
import com.tangerine.theatre_manager.performance.service.PerformanceResponses;
import com.tangerine.theatre_manager.performance.service.PerformanceService;
import com.tangerine.theatre_manager.performance.service.dto.PerformanceResponse;
import java.net.URI;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/performances", produces = APPLICATION_JSON_VALUE)
public class PerformanceRestController {

    //TODO : 권한 별 필터 어노테이션 붙여주기

    private final PerformanceService performanceService;

    public PerformanceRestController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<PerformanceResponse> createPerformance(
            @Validated @RequestBody PerformanceRequest request
    ) {
        PerformanceResponse response = performanceService.registerPerformance(PerformanceRequest.to(request));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{performanceId}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity
                .status(CREATED)
                .location(location)
                .body(response);
    }

    @PutMapping(path = "/{performanceId}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<PerformanceResponse> updatePerformance(
            @PathVariable Long performanceId,
            @Validated @RequestBody PerformanceRequest request
    ) {
        PerformanceResponse response = performanceService.editPerformance(
                performanceId, PerformanceRequest.to(request));
        return ResponseEntity
                .status(OK)
                .body(response);
    }

    @DeleteMapping(path = "/{performanceId}")
    public ResponseEntity<Void> deletePerformance(
            @PathVariable Long performanceId
    ) {
        performanceService.removePerformance(performanceId);
        return ResponseEntity
                .status(NO_CONTENT)
                .build();
    }

    @GetMapping(path = "/{performanceId}")
    public ResponseEntity<PerformanceResponse> readPerformance(
            @PathVariable Long performanceId
    ) {
        PerformanceResponse response = performanceService.findPerformance(performanceId);
        return ResponseEntity
                .status(OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<PerformanceResponses> readPerformanceByTitle(
            @RequestParam String title,
            Pageable pageable
    ) {
        PerformanceResponses responses = performanceService.findPerformanceByTitle(title, pageable);
        return ResponseEntity
                .status(OK)
                .body(responses);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<PerformanceResponses> readAllPerformance(
            Pageable pageable
    ) {
        PerformanceResponses response = performanceService.findAllPerformances(pageable);
        return ResponseEntity
                .status(OK)
                .body(response);
    }

}
