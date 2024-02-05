package com.group1.ecocredit.models;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PasswordResetNewPassword {

    String email;
    String newPassword;
    String newPasswordRepeat;
}
