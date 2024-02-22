package com.group1.ecocredit.dto;


import com.group1.ecocredit.models.Address;
import lombok.Data;

@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private AddressBD address;

    @Data
    public static class AddressBD {
        private String street;
        private String city;
        private String province;
        private String postalCode;
    }
}
