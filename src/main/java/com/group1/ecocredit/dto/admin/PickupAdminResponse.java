package com.group1.ecocredit.dto.admin;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class PickupAdminResponse {
    private Integer id;
    private LocalDateTime dateTime;
    private Integer userId;
    private String status;
    private List<WasteAdminResponse> wastes;
}
