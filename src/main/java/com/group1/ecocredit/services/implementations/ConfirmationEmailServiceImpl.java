package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.ConfirmationEmail;
import com.group1.ecocredit.repositories.ConfirmationEmailRepository;
import com.group1.ecocredit.services.ConfirmationEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationEmailServiceImpl implements ConfirmationEmailService {
    @Autowired
    private ConfirmationEmailRepository confirmationEmailRepository;

    @Override
    public ConfirmationEmail save(ConfirmationEmail confirmationEmail) {
       return confirmationEmailRepository.save(confirmationEmail);
    }
}
