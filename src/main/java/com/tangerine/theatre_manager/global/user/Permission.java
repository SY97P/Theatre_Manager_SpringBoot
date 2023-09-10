package com.tangerine.theatre_manager.global.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "permissions")
public class Permission {

  @Id
  private Long id;

  @Column(nullable = false, length = 10)
  private String name;

  protected Permission() {
  }

  public Permission(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Permission{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
