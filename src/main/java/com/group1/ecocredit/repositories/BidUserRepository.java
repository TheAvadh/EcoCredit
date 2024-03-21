package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.BidUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidUserRepository extends JpaRepository<BidUser, Long> {
    List<BidUser> findByUserId(Integer userId);
}
