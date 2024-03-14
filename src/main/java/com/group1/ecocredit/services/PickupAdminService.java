package com.group1.ecocredit.services;

import com.group1.ecocredit.dto.admin.PickupAdminResponse;

import java.sql.SQLException;
import java.util.List;

public interface PickupAdminService {
    List<PickupAdminResponse> getScheduledPickups() throws SQLException;
}
