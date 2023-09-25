package com.tangerine.theatre_manager.global.auth;

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
public class Email {

    @jakarta.validation.constraints.Email
    @NotBlank
    @Column(name = "email", nullable = false)
    private String address;

    public Email(String address) {
        String REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Assert.isTrue(StringUtils.isNotBlank(address), "이메일 주소는 반드시 주어져야 합니다.");
        Assert.isTrue(address.matches(REGEX), "형식에 맞지 않는 이메일입니다.");
        this.address = address;
    }
}
