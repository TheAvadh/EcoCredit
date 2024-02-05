package com.group1.ecocredit.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name="passwordresettoken")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Setter
    private String token;

    @Setter
    private LocalDateTime expirationTime;

    @Setter
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private EcoCreditUser user;

}
