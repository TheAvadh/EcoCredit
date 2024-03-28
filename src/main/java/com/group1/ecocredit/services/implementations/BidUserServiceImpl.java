package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.BidUser;
import com.group1.ecocredit.repositories.BidUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidUserServiceImpl implements com.group1.ecocredit.services.BidUserService {
    @Autowired
    private BidUserRepository bidUserRepository;

    @Override
    public List<BidUser> findByUserId(Integer userId) {
        return bidUserRepository.findByUserId(userId);
    }

    @Override
    public List<BidUser> findByActive(boolean isActive) {
        return bidUserRepository.findByActive(isActive);
    }

    @Override
    public BidUser save(BidUser bidUser) {
        return bidUserRepository.save(bidUser);
    }

    @Override
    public List<BidUser> findAll() {
        return bidUserRepository.findAll();
    }
}
