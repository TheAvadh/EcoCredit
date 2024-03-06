package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.ScheduledPickupsWithoutConfirmationEmailSent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PickupRepository extends JpaRepository<Pickup, Long> {

    @Query("SELECT NEW com.example.ScheduledPickupsWithConfirmationEmailSent(p.id, p.someAttribute, ce.someAttribute) " +
            "FROM Pickup p " +
            "LEFT JOIN ConfirmationEmail ce ON p.id = ce.pickup.id " +
            "WHERE p.status.value = :scheduledStatus AND ce.pickup.id IS NULL")
    List<ScheduledPickupsWithoutConfirmationEmailSent> findScheduledPickupsWithoutConfirmationEmailSent(
            @Param("scheduledStatus") String scheduledStatus);
}
