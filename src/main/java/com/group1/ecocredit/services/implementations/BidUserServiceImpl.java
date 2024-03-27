package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.BidUser;
import com.group1.ecocredit.repositories.BidUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidUserServiceImpl implements com.group1.ecocredit.services.BidUserService {
    @Autowired
    private BidUserService bidUserRepository;

    @Override
    public List<BidUser> findByUserId(Integer userId) {
        return bidUserRepository.findByUserId(userId);
    }

    @Override
    public List<BidUser> findByActive(boolean isActive) {
        return bidUserRepository.findByActive(isActive);
    }
}
