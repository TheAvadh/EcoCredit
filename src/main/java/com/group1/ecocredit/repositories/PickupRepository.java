package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PickupRepository extends JpaRepository<Pickup, Long> {

    @Query("SELECT p FROM Pickup p "
            + "WHERE p.status = :scheduledStatus "
            + "AND p.id NOT IN (SELECT ce.pickup.id FROM ConfirmationEmail ce)")
    List<Pickup> findScheduledPickupsWithoutConfirmationEmailSent(
            @Param("scheduledStatus") String scheduledStatus);
}
