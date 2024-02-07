package com.group1.ecocredit.services;

import com.group1.ecocredit.dto.ForgetPasswordRequest;
import com.group1.ecocredit.models.PasswordReset;
import com.group1.ecocredit.models.PasswordResetToken;
import com.group1.ecocredit.models.User;

public interface PasswordService {
    boolean forgetPassword(ForgetPasswordRequest request);

    PasswordResetToken getPasswordResetToken(User user);

    boolean isValidToken(PasswordResetToken token);


    void inValidateToken(String token);

    boolean validPasswordResetRequest(String token);

    boolean resetPassword(String token, PasswordReset request);
}
