package com.group1.ecocredit.services;

import com.group1.ecocredit.dto.ForgetPasswordRequest;
import com.group1.ecocredit.dto.PasswordResetRequest;

public interface PasswordService {
    boolean forgetPassword(ForgetPasswordRequest request);

    boolean validPasswordResetRequest(String token);

    boolean resetPassword(String token, PasswordResetRequest request);
}
