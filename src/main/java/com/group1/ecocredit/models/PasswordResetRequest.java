package com.group1.ecocredit.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ecocredit_password_reset_requests")
@Getter
@Setter
public class PasswordResetRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String requestId;

    String email;
}
