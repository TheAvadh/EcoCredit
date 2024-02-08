package com.group1.ecocredit.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private AddressDTO address;

    @Data
    public static class AddressDTO {
        private String street;
        private String city;
        private String province;
        private String postalCode;
        private String country;
    }
}
