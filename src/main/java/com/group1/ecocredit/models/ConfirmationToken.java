package com.group1.ecocredit.models;

import jakarta.persistence.*;
import lombok.*;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Setter
    private String token;

    @Column(nullable = false)
    @Setter
    private LocalDateTime createdTime;

    @Column(nullable = false)
    @Setter
    private LocalDateTime expirationTime;


    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @Column(nullable = false)
    @Setter
    boolean used = false;

    public ConfirmationToken(String token,
                             LocalDateTime createdTime,
                             LocalDateTime expirationTime,
                             User user) {
        this.token = token;
        this.createdTime = createdTime;
        this.expirationTime = expirationTime;
        this.user = user;
        this.used = false;
    }
}
