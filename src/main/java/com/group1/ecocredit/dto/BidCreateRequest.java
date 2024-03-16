package com.group1.ecocredit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidCreateRequest {
    private String dateTime;
    private Long wasteId;
}
