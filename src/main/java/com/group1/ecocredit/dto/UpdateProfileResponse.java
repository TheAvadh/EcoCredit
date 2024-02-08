package com.group1.ecocredit.dto;

import lombok.Data;

@Data
public class UpdateProfileResponse {
    public enum ResponseType {
        SUCCESS,
        USER_NOT_FOUND,
        INTERNAL_SERVER_ERROR
    }

    private ResponseType response;
}
