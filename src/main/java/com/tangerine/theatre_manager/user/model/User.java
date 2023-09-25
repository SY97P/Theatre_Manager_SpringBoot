package com.tangerine.theatre_manager.user.model;


import com.tangerine.theatre_manager.global.auth.Role;
import com.tangerine.theatre_manager.performance.model.vo.AgeRate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
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

    private List<Role> roles;

    public User(String email, AgeRate ageRate, List<Role> roles) {
        this.email = email;
        this.ageRate = ageRate;
        this.roles = roles;
    }

    public String getAgeRateName() {
        return ageRate.name();
    }

    public List<String> getRoleNames() {
        return roles.stream()
                .map(Role::name)
                .toList();
    }

    public void setAgeRate(AgeRate ageRate) {
        this.ageRate = ageRate;
    }

    public User addRole(Role role) {
        if (!this.roles.contains(role)) {
            this.roles.add(role);
        }
        return this;
    }
}
