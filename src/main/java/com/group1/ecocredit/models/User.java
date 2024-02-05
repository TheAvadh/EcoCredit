package com.group1.ecocredit.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user")
public class User {

    @Id
    Long id;

    String email;

    String firstName;

    String lastName;

    String passwordHash;
}
