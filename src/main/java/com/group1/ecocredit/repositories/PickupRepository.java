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
            "(SELECT ce FROM ConfirmationEmail ce WHERE ce.pickup.id = p.id AND ce.emailSent = true) " +
            "AND DATE(p.dateTime) = CURRENT_DATE() " +
            "AND p.status.value = 'SCHEDULED'")
    List<Pickup> findAllPickupsWithEmailsNotSent();

    @Query("""
            select new com.group1.ecocredit.models.admin.PickupQueryResult(
            p.id, p.dateTime, 
            u.firstName, u.lastName,
            w.id, w.weight,
            c.value, s.value)
            from Pickup p
            inner join Waste w on p.id = w.pickup.id
            inner join Category c on w.category.id = c.id
            inner join Status s on p.status.id = s.id
            inner join User u on p.user.id = u.id
            where s.value like 'SCHEDULED'
            order by p.dateTime desc, p.id asc, w.id asc""")
    List<PickupQueryResult> findScheduledPickups();

    @Query("""
            select new com.group1.ecocredit.models.admin.PickupQueryResult(
            p.id, p.dateTime,
            u.firstName, u.lastName,
            w.id, w.weight,
            c.value, s.value)
            from Pickup p
            inner join Waste w on p.id = w.pickup.id
            inner join Category c on w.category.id = c.id
            inner join Status s on p.status.id = s.id
            inner join User u on p.user.id = u.id
            where s.value like 'COMPLETED'
            order by p.dateTime desc, p.id asc, w.id asc""")
    List<PickupQueryResult> findCompletedPickups();

    @Query("""
            select new com.group1.ecocredit.models.admin.PickupQueryResult(
            p.id, p.dateTime,
            u.firstName, u.lastName,
            w.id, w.weight,
            c.value, s.value)
            from Pickup p
            inner join Waste w on p.id = w.pickup.id
            inner join Category c on w.category.id = c.id
            inner join Status s on p.status.id = s.id
            inner join User u on p.user.id = u.id
            where s.value like 'IN_PROGRESS'
            order by p.dateTime desc, p.id asc, w.id asc""")
    List<PickupQueryResult> findInProgressPickups();
}
