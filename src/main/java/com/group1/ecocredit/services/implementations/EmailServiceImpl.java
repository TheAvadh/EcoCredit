package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.config.EmailConfig;
import com.group1.ecocredit.services.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final EmailConfig emailConfig;

    public EmailServiceImpl(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    @Override
    public void sendVerifyAccountEmail(String email) throws MessagingException {
        var subject = "Verify your Eco Credit account";
        var text = """
            <div>
              Click <a href="http://localhost:8080/api/verify-account?email=%s" target="_blank">here</a>
               to verify your Eco Credit account
            </div>
            """.formatted(email);
        var isHtml = true;

        sendEmail(email, subject, text, isHtml);
    }

    @Override
    public void sendResetPasswordEmail(String email, String token) throws MessagingException {
        var subject = "Reset your Eco Credit password";
        var text = """
            <div>
              Click <a href="http://localhost:8080/api/reset-password/%s" target="_blank">here</a>
               to reset your Eco Credit password
            </div>
            """.formatted(token);
        var isHtml = true;

        sendEmail(email, subject, text, isHtml);
    }

    private void sendEmail(String email, String subject,
                            String text, boolean isHtml) throws MessagingException {
        var javaMailSender = emailConfig.javaMailSender();
        var mimeMessage = javaMailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtml);

        javaMailSender.send(mimeMessage);
    }
}
