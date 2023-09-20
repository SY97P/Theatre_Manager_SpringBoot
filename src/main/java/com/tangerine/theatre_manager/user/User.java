package com.tangerine.theatre_manager.user;


import com.tangerine.theatre_manager.global.auth.Role;
import com.tangerine.theatre_manager.performance.model.vo.AgeRate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(value = EnumType.STRING)
    private AgeRate ageRate;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public User(String email, AgeRate ageRate, Role role) {
        this.email = email;
        this.ageRate = ageRate;
        this.role = role;
    }

    public String getRoleName() {
        return role.name();
    }

    public void setAgeRate(AgeRate ageRate) {
        this.ageRate = ageRate;
    }
}
