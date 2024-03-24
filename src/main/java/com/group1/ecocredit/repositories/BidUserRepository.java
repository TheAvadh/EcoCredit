package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.BidUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidUserRepository extends JpaRepository<BidUser, Long> {
    List<BidUser> findByUserId(Integer userId);

    @Query("SELECT b FROM Bid_all b WHERE b.is_active = :isActive")
    List<BidUser> findByActive(boolean isActive);
}
