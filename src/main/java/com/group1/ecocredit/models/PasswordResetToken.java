package com.group1.ecocredit.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name="passwordresettoken")
public class PasswordResetToken {

    @Id
    private UUID id;

    @Setter
    private String token;

    @Setter
    private LocalDateTime expirationTime;

    @Setter
    @OneToOne
    private User user;

}
