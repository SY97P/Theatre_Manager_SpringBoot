package com.tangerine.theatre_manager.performance.model.vo;

import static com.tangerine.theatre_manager.global.exception.ErrorCode.FORBIDDEN_AGE;

import com.tangerine.theatre_manager.global.exception.ForbiddenAgeException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum AgeRate {
    KID_AVAILABLE(14),
    TEEN_AVAILABLE(19),
    ADULT_AVAILABLE(90);

    private final int ageBound;

    AgeRate(int ageBound) {
        this.ageBound = ageBound;
    }

    public static boolean isAvailableForViewing(AgeRate ageRate, String ageRange) {
        try {
            int upperBound = Integer.parseInt(ageRange.split("~")[1]);
            return ageRate.getAgeBound() < upperBound;
        } catch (ArrayIndexOutOfBoundsException exception) {
            return true;
        }
    }

    public static AgeRate parseRangeToRate(String ageRange) {
        try {
            int upperBound = Integer.parseInt(ageRange.split("~")[1]);
            return Arrays.stream(AgeRate.values())
                    .filter(ageRate -> ageRate.getAgeBound() > upperBound)
                    .findFirst()
                    .orElseThrow(() -> new ForbiddenAgeException(FORBIDDEN_AGE));
        } catch (ArrayIndexOutOfBoundsException exception) {
            return AgeRate.ADULT_AVAILABLE;
        }
    }
}
