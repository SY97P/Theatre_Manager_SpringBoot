package com.tangerine.theatre_manager.performance.model;

import com.tangerine.theatre_manager.global.price.Price;
import com.tangerine.theatre_manager.performance.model.vo.AgeRate;
import com.tangerine.theatre_manager.performance.model.vo.Genre;
import com.tangerine.theatre_manager.performance.model.vo.Stage;
import com.tangerine.theatre_manager.performance.model.vo.Title;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "performances")
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Title title;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @Enumerated(value = EnumType.STRING)
    private AgeRate ageRate;

    @Enumerated(value = EnumType.STRING)
    private Stage stage;

    @Embedded
    private Price price;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "open_run", nullable = false)
    private LocalDate openRun;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "close_run", nullable = false)
    private LocalDate closeRun;

    public Performance(Title title, Genre genre, AgeRate ageRate, Stage stage, Price price,
            LocalDate openRun, LocalDate closeRun) {
        this.title = title;
        this.genre = genre;
        this.ageRate = ageRate;
        this.stage = stage;
        this.price = price;
        this.openRun = openRun;
        this.closeRun = closeRun;
    }

    public String getTitleValue() {
        return title.getValue();
    }

    public String getGenreName() {
        return genre.name();
    }

    public String getAgeRateName() {
        return ageRate.name();
    }

    public String getStageName() {
        return stage.name();
    }

    public long getPriceValue() {
        return price.getValue();
    }

    public Performance update(Performance performance) {
        this.title = performance.getTitle();
        this.genre = performance.getGenre();
        this.ageRate = performance.getAgeRate();
        this.stage = performance.getStage();
        this.price = performance.getPrice();
        this.openRun = performance.getOpenRun();
        this.closeRun = performance.getCloseRun();
        return this;
    }
}
