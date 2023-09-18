package com.tangerine.theatre_manager.performance.model.vo;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Title {

    @NotBlank
    @Column(name = "title", nullable = false)
    private String value;

    public Title(String value) {
        Assert.isTrue(StringUtils.isNotBlank(value), "공연 타이틀이 주어지지 않았습니다.");
        this.value = value;
    }
}
