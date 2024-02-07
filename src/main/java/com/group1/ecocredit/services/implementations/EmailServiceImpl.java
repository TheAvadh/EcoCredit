package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendProfileUpdateNotification(User user) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper  = new MimeMessageHelper(mimeMessage, true); // 'true' indicates multipart message for attachments

            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setSubject("Profile Updated Notification");

            String htmlContent = "Dear " + user.getFirstname() + " " + user.getLastname() + ",<br><br>Your profile has been successfully updated.";
            mimeMessageHelper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

