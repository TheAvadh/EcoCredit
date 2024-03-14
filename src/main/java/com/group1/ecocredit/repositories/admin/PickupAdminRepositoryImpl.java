package com.group1.ecocredit.repositories.admin;

import com.group1.ecocredit.models.admin.PickupQueryResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PickupAdminRepositoryImpl implements PickupAdminRepository {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Override
    public List<PickupQueryResult> findPickups() throws SQLException {
        var conn = DriverManager.getConnection(
                dbUrl, dbUsername, dbPassword
        );

        var query = """
                    select
                        p.id, p.datetime, p.user_id,
                        w.id as waste_id, w.weight,
                        cl.value as category,
                        sl.value as status
                    from
                        pickup p
                        inner join waste w
                        inner join category_lookup cl
                        inner join status_lookup sl
                    on p.id = w.pickup_id and w.category_id = cl.id and p.status_id = sl.id
                    where sl.value like 'SCHEDULED'
                    order by datetime desc, id asc, waste_id asc;
            """;
        var statement = conn.prepareStatement(query);
        var resultSet = statement.executeQuery();
        List<PickupQueryResult> pickups = new ArrayList<>();

        while (resultSet.next()) {
            var pickup = new PickupQueryResult();
            pickup.setId(resultSet.getInt("id"));
            pickup.setDateTime(resultSet
                    .getTimestamp("datetime").toLocalDateTime());
            pickup.setUserId(resultSet.getInt("user_id"));
            pickup.setWasteId(resultSet.getLong("waste_id"));
            pickup.setWeight(resultSet.getFloat("weight"));
            pickup.setCategory(resultSet.getString("category"));
            pickup.setStatus(resultSet.getString("status"));
            pickups.add(pickup);
        }

        conn.close();
        return pickups;
    }
}
