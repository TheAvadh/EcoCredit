package com.group1.ecocredit.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone_number;
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
