package com.group1.ecocredit.models;

import lombok.Data;

@Data
public class Address {
    private String street;
    private String city;
    private String province;
    private String postalCode;
    private String country;
}
