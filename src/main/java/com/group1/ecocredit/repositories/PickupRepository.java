package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.admin.PickupQueryResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PickupRepository extends JpaRepository<Pickup, Long> {
    List<Pickup> findByUserId(Long userId);

    @Query("SELECT p FROM Pickup p " +
            "WHERE NOT EXISTS " +
            "(SELECT ce FROM ConfirmationEmail ce WHERE ce.pickup.id = p.id AND ce.emailSent = true)")
    List<Pickup> findAllPickupsWithEmailsNotSent();

    @Query("""
            select new com.group1.ecocredit.models.admin.PickupQueryResult(
            p.id, p.dateTime, p.user.id,
            w.id, w.weight,
            c.value, s.value)
            from Pickup p
            inner join Waste w on p.id = w.pickup.id
            inner join Category c on w.category.id = c.id
            inner join Status s on p.status.id = s.id
            where s.value like 'SCHEDULED'
            order by p.dateTime desc, p.id asc, w.id asc""")
    List<PickupQueryResult> findScheduledPickups();
}
