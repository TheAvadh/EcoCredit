package com.group1.ecocredit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.annotation.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisplayBidRequest {

    private Long bidId;
    @Nullable
    private Double newBidAmount;
}
