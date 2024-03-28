package com.group1.ecocredit.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class PasswordResetRequest {

    String email;
    String newPassword;
    String newPasswordRepeat;
}
