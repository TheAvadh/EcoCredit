package com.group1.ecocredit.services.admin;

import com.group1.ecocredit.dto.admin.WasteUpdateRequest;

public interface WasteService {
    boolean updateWeight(Long id, WasteUpdateRequest request);
}
