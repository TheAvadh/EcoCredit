package com.group1.ecocredit.services;

import com.group1.ecocredit.dto.ForgetPasswordRequest;
import com.group1.ecocredit.dto.PasswordResetRequest;
import com.group1.ecocredit.models.PasswordResetToken;
import com.group1.ecocredit.models.User;

public interface PasswordService {
    boolean forgetPassword(ForgetPasswordRequest request);

    boolean validPasswordResetRequest(String token);

    boolean resetPassword(String token, PasswordResetRequest request);
}
