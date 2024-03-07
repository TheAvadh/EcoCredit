package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PickupRepository extends JpaRepository<Pickup, Long> {

    @Query("SELECT p FROM Pickup p " +
            "WHERE NOT EXISTS " +
            "(SELECT ce FROM ConfirmationEmail ce WHERE ce.pickup.id = p.id AND ce.emailSent = true)")
    List<Pickup> findAllPickupsWithEmailsNotSent();
}
