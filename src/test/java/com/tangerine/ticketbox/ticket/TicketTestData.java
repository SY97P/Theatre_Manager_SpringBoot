package com.tangerine.ticketbox.ticket;

import com.tangerine.ticketbox.theatre.model.AgeRate;
import com.tangerine.ticketbox.theatre.model.Genre;
import com.tangerine.ticketbox.theatre.model.Stage;
import com.tangerine.ticketbox.theatre.model.TheatreName;
import com.tangerine.ticketbox.theatre.repository.model.Theatre;
import com.tangerine.ticketbox.ticket.model.Price;
import com.tangerine.ticketbox.ticket.model.Quantity;
import com.tangerine.ticketbox.ticket.repository.Ticket;
import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class TicketTestData {

    public static Ticket newDomain(Ticket ticket) {
        return new Ticket(ticket.ticketId(), ticket.theatreId(), new Price(1), new Quantity(1), LocalDate.now());
    }

    public static List<Theatre> theatreDomains = List.of(
            new Theatre(UUID.randomUUID(), new TheatreName("밤의 여왕 아리아"), Genre.OPERA, AgeRate.ALL, LocalDate.of(2023, 1, 23), LocalDate.of(2023, 8, 14), Stage.A1),
            new Theatre(UUID.randomUUID(), new TheatreName("쉬어매드니스"), Genre.PLAY, AgeRate.FIFTEEN, LocalDate.of(2022, 6, 1), LocalDate.of(2023, 10, 31), Stage.A2),
            new Theatre(UUID.randomUUID(), new TheatreName("시라노"), Genre.MUSICAL, AgeRate.ADULT_ONLY, LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 1), Stage.B1),
            new Theatre(UUID.randomUUID(), new TheatreName("라면"), Genre.PLAY, AgeRate.FIFTEEN, LocalDate.of(2023, 10, 23), LocalDate.of(2024, 3, 20), Stage.B2)
    );

    public static List<Ticket> ticketDomains = List.of(
            new Ticket(UUID.randomUUID(), theatreDomains.get(0).theatreId(), new Price(1000), new Quantity(2), LocalDate.now()),
            new Ticket(UUID.randomUUID(), theatreDomains.get(1).theatreId(), new Price(28000), new Quantity(1), LocalDate.now()),
            new Ticket(UUID.randomUUID(), theatreDomains.get(2).theatreId(), new Price(3000), new Quantity(22), LocalDate.now()),
            new Ticket(UUID.randomUUID(), theatreDomains.get(3).theatreId(), new Price(55000), new Quantity(8), LocalDate.now())
    );

    public static Stream<Arguments> provideDomains() {
        return ticketDomains.stream()
                .map(Arguments::of);
    }
}
