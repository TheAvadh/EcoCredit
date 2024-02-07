package com.group1.ecocredit.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "ecocredit_user")
public class EcoCreditUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    String email;

    @NotBlank
    String firstName;

    String lastName;

    @NotBlank
    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    Password password;
}
