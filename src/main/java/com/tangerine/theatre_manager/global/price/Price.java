package com.tangerine.theatre_manager.global.price;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Price {

    @Positive
    @Column(name = "price", nullable = false)
    private long value;

    public Price(long value) {
        Assert.isTrue(value >= 0, "가격은 양수여야 합니다.");
        this.value = value;
    }
}
