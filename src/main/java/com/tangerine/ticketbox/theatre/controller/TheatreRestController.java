package com.tangerine.ticketbox.theatre.controller;

import com.tangerine.ticketbox.theatre.controller.dto.CreateTheatreRequest;
import com.tangerine.ticketbox.theatre.controller.dto.TheatreResponse;
import com.tangerine.ticketbox.theatre.controller.dto.UpdateTheatreRequest;
import com.tangerine.ticketbox.theatre.controller.mapper.TheatreControllerMapper;
import com.tangerine.ticketbox.theatre.vo.TheatreName;
import com.tangerine.ticketbox.theatre.service.TheatreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/theatres")
public class TheatreRestController {

    private final TheatreService service;

    public TheatreRestController(TheatreService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public void registerTheatre(@RequestBody CreateTheatreRequest request) {
        service.createTheatre(TheatreControllerMapper.INSTANCE.requestToParam(request));
    }

    @GetMapping("/update")
    public void updateTheatre(@RequestBody UpdateTheatreRequest request) {
        service.updateTheatre(TheatreControllerMapper.INSTANCE.requestToParam(request));
    }

    @GetMapping("/unregister/all")
    public void unregisterTheatres() {
        service.deleteAllTheatre();
    }

    @GetMapping("/unregister/{theatreId}")
    public void unregisterTheatreById(@PathVariable UUID theatreId) {
        service.deleteTheatreById(theatreId);
    }

    @GetMapping("")
    public ResponseEntity<List<TheatreResponse>> theatreList() {
        return ResponseEntity.ok(
                service.findAllTheatre()
                        .stream()
                        .map(TheatreControllerMapper.INSTANCE::resultToResponse)
                        .toList());
    }

    @GetMapping("/id/{theatreId}")
    public ResponseEntity<TheatreResponse> theatreById(@PathVariable UUID theatreId) {
        return ResponseEntity.ok(TheatreControllerMapper.INSTANCE.resultToResponse(service.findTheatreById(theatreId)));
    }

    @GetMapping("/name/{theatreName}")
    public ResponseEntity<TheatreResponse> theatreByName(@PathVariable TheatreName theatreName) {
        return ResponseEntity.ok(TheatreControllerMapper.INSTANCE.resultToResponse(service.findTheatreByName(theatreName)));
    }

    // todo -> 500 internet server error
    @GetMapping("/date/{date}")
    public ResponseEntity<TheatreResponse> theatreByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(TheatreControllerMapper.INSTANCE.resultToResponse(service.findTheatreByDate(date)));
    }
}
