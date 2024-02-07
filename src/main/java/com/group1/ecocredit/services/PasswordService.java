package com.group1.ecocredit.services;

import com.group1.ecocredit.dto.ForgetPasswordRequest;

public interface PasswordService {
    boolean forgetPassword(ForgetPasswordRequest request);
}
