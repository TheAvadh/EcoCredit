package com.group1.ecocredit.models;

import lombok.*;
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
@Getter
@Setter
@Entity
@Table(name = "password_reset_request")
public class PasswordResetRequest {
    @Id
    @NotNull
    String requestId;

    @NotNull
    Integer userId;

    @NotNull
    String email;

    @NotNull
    LocalDateTime expiry;
}
