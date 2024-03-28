package com.group1.ecocredit.services;

import com.group1.ecocredit.models.BidUser;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BidUserService {
    List<BidUser> findByUserId(Integer userId);

    List<BidUser> findByActive(boolean isActive);

    BidUser save(BidUser bidUser);

    List<BidUser> findAll();
}
