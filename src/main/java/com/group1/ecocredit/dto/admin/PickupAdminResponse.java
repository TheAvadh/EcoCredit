package com.group1.ecocredit.dto.admin;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class PickupAdminResponse {
    private Long id;
    private String date;
    private String time;
    private String userFirstName;
    private String userLastName;
    private String status;
    private List<WasteAdminResponse> wastes;
}
