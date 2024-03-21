package com.group1.ecocredit.dto.admin;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WasteAdminResponse {
    private Long wasteId;
    private Float weight;
    private String category;
}
