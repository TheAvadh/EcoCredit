package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PickupRepository extends JpaRepository<Pickup, Long> {

    @Query("SELECT p FROM Pickup p "
            + "WHERE p.status.value = :scheduledStatus "
            + "AND p.id NOT IN (SELECT ce.pickup.id FROM ConfirmationEmail ce)")
    List<Pickup> findScheduledPickupsWithoutConfirmationEmailSent(
            @Param("scheduledStatus") String scheduledStatus);
}
