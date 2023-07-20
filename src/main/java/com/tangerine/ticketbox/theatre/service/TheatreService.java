package com.tangerine.ticketbox.theatre.service;

import com.tangerine.ticketbox.theatre.model.TheatreName;
import com.tangerine.ticketbox.theatre.repository.TheatreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TheatreService {

    private final TheatreRepository repository;

    public TheatreService(TheatreRepository repository) {
        this.repository = repository;
    }

    public void createTheatre(TheatreParam param) {
        repository.insert(TheatreServiceMapper.INSTANCE.paramToDomain(param));
    }

    public void updateTheatre(TheatreParam param) {
        repository.update(TheatreServiceMapper.INSTANCE.paramToDomain(param));
    }

    public void deleteAllTheatre() {
        repository.deleteAll();
    }

    public void deleteTheatreById(UUID theatreId) {
        repository.deleteById(theatreId);
    }

    public List<TheatreResult> findAllTheatre() {
        return repository.findAll()
                .stream()
                .map(TheatreServiceMapper.INSTANCE::domainToResult)
                .toList();
    }

    public TheatreResult findTheatreById(UUID theatreId) {
        return TheatreServiceMapper.INSTANCE.domainToResult(repository.findById(theatreId));
    }

    public TheatreResult findTheatreByName(TheatreName theatreName) {
        return TheatreServiceMapper.INSTANCE.domainToResult(repository.findByName(theatreName));
    }

    public TheatreResult findTheatreByDate(LocalDate date) {
        return TheatreServiceMapper.INSTANCE.domainToResult(repository.findByDate(date));
    }

}
