package com.group1.ecocredit.dto;

import com.group1.ecocredit.models.Address;
import lombok.Data;

@Data
public class UserDetailsResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Address address;
}
