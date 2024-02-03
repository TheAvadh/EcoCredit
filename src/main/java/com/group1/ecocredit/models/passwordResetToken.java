package com.group1.ecocredit.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

public class passwordResetToken {

    @Getter
    private UUID id;

    @Setter
    @Getter
    private String token;

    @Setter
    @Getter
    private LocalDateTime expirationTime;

    @Setter
    @Getter
    private User user;

}
