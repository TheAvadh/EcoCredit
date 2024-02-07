package com.group1.ecocredit.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "password_reset_request")
public class PasswordResetRequest {
    @Id
    @NotNull
    String requestId;

    @NotNull
    Integer userId;

    @NotNull
    LocalDateTime expiry;
}
