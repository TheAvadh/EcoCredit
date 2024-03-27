package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.ConfirmationEmail;
import com.group1.ecocredit.repositories.ConfirmationEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationEmailServiceImpl implements com.group1.ecocredit.services.ConfirmationEmailService {
    @Autowired
    private ConfirmationEmailService confirmationEmailRepository;

    @Override
    public void save(ConfirmationEmail confirmationEmail) {
       confirmationEmailRepository.save(confirmationEmail);
    }
}
