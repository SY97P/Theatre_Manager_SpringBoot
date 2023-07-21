package com.tangerine.ticketbox.theatre.repository;

import com.tangerine.ticketbox.theatre.repository.model.Theatre;
import com.tangerine.ticketbox.theatre.vo.TheatreName;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TheatreRepository {

    void insert(Theatre theatre);

    void update(Theatre theatre);

    void deleteAll();

    void deleteById(UUID theatreId);

    List<Theatre> findAll();

    Theatre findById(UUID theatreId);

    Theatre findByName(TheatreName theatreName);

    Theatre findByDate(LocalDate date);

}
