package com.group1.ecocredit.models;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PasswordReset {

    String email;
    String newPassword;
    String newPasswordRepeat;
}
