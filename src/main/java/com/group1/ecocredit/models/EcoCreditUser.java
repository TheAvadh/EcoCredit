package com.group1.ecocredit.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "ecocredit_user")
public class EcoCreditUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String email;

    String firstName;

    String lastName;

    @OneToOne(fetch = FetchType.EAGER)
    Password password;
}
