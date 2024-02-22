package com.group1.ecocredit.dto;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PasswordResetRequest {

    String email;
    String newPassword;
    String newPasswordRepeat;
}
