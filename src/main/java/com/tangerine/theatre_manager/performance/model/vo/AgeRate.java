package com.tangerine.theatre_manager.performance.model.vo;

public enum AgeRate {
    ALL(14),
    TEEN(19),
    ADULT_ONLY(90);

    private final int ageBound;

    AgeRate(int ageBound) {
        this.ageBound = ageBound;
    }

    public static boolean isAvailableForViewing(AgeRate ageRate, String ageRange) {
        int upperBound = Integer.parseInt(ageRange.split("~")[1]);
        return ageRate.getAgeBound() < upperBound;
    }

    public int getAgeBound() {
        return ageBound;
    }
}
