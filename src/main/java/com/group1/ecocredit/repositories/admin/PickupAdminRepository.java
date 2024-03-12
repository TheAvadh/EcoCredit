package com.group1.ecocredit.repositories.admin;

import com.group1.ecocredit.models.admin.PickupQueryResult;

import java.sql.SQLException;
import java.util.List;

public interface PickupAdminRepository {
    List<PickupQueryResult> findPickups() throws SQLException;
}
