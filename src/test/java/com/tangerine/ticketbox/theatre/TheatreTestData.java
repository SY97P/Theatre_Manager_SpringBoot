package com.tangerine.ticketbox.theatre;

import com.tangerine.ticketbox.theatre.vo.*;
import com.tangerine.ticketbox.theatre.repository.model.Theatre;
import com.tangerine.ticketbox.theatre.service.dto.TheatreParam;
import com.tangerine.ticketbox.theatre.service.mapper.TheatreServiceMapper;
import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class TheatreTestData {

    public static Theatre newDomain(Theatre theatre) {
        return new Theatre(theatre.theatreId(), new TheatreName("새로운 연극"), Genre.PLAY, AgeRate.ALL, LocalDate.of(2023, 4, 12), LocalDate.of(2023, 9, 1), Stage.B1);
    }

    public static TheatreParam newParams(TheatreParam theatre) {
        return new TheatreParam(theatre.theatreId(), new TheatreName("새로운 연극"), Genre.PLAY, AgeRate.ALL, LocalDate.of(2023, 4, 12), LocalDate.of(2023, 9, 1), Stage.B1);
    }

    public static List<TheatreParam> theatreParams = List.of(
            new TheatreParam(UUID.randomUUID(), new TheatreName("밤의 여왕 아리아"), Genre.OPERA, AgeRate.ALL, LocalDate.of(2023, 1, 23), LocalDate.of(2023, 8, 14), Stage.A1),
            new TheatreParam(UUID.randomUUID(), new TheatreName("쉬어매드니스"), Genre.PLAY, AgeRate.FIFTEEN, LocalDate.of(2022, 6, 1), LocalDate.of(2023, 10, 31), Stage.A2),
            new TheatreParam(UUID.randomUUID(), new TheatreName("시라노"), Genre.MUSICAL, AgeRate.ADULT_ONLY, LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 1), Stage.B1),
            new TheatreParam(UUID.randomUUID(), new TheatreName("라면"), Genre.PLAY, AgeRate.FIFTEEN, LocalDate.of(2023, 10, 23), LocalDate.of(2024, 3, 20), Stage.B2)
    );

    public static List<Theatre> theatreDomains = theatreParams.stream()
            .map(TheatreServiceMapper.INSTANCE::paramToDomain)
            .toList();

    static Stream<Arguments> provideParams() {
        return theatreParams.stream()
                .map(Arguments::of);
    }

    static Stream<Arguments> provideDomains() {
        return theatreParams.stream()
                .map(TheatreServiceMapper.INSTANCE::paramToDomain)
                .map(Arguments::of);
    }
}
