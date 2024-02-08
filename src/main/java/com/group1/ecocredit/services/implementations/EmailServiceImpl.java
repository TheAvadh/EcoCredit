package com.group1.ecocredit.services.implementations;


import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import com.group1.ecocredit.config.EmailConfig;
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
              Click <a href="http://localhost:8080/api/reset-password?ID=%s" target="_blank">here</a>
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
