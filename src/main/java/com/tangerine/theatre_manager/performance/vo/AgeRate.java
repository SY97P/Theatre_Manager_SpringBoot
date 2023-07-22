package com.tangerine.theatre_manager.performance.vo;

public enum AgeRate {
    ALL(0),
    FIFTEEN(15),
    ADULT_ONLY(19);

    private final int age;

    AgeRate(int age) {
        this.age = age;
    }
}
