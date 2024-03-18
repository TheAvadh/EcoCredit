package com.group1.ecocredit.services.implementations;


import com.group1.ecocredit.models.Pickup;
import com.group1.ecocredit.models.User;
import com.group1.ecocredit.services.EmailService;
import jakarta.mail.MessagingException;
import com.group1.ecocredit.config.EmailConfig;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendProfileUpdateEmail(User user) throws MessagingException {
        var subject = "Eco Credit Profile Updated";
        var text = """
                <div>
                  Dear %s %s, your profile has been successfully updated.
                </div>
                """.formatted(user.getFirstName(), user.getLastName());
        var isHtml = true;

        sendEmail(user.getEmail(), subject, text, isHtml);
    }

    private final EmailConfig emailConfig;

    public EmailServiceImpl(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    @Override
    public void sendVerifyAccountEmail(String email, String token) throws MessagingException {
        var subject = "Verify your Eco Credit account";
        var text = """
            <div>
              Click <a href="http://172.17.0.157:8080/api/v1/auth/verify-account?token=%s" target="_blank">here</a>
               to verify your Eco Credit account
            </div>
            """.formatted(token);
        var isHtml = true;

        sendEmail(email, subject, text, isHtml);
    }

    @Override
    public void sendResetPasswordEmail(String email, String token) throws MessagingException {
        var subject = "Reset your Eco Credit password";
        var text = """
            <div>
              Click <a href="http://172.17.0.157:8080/api/v1/auth/reset-password?token=%s" target="_blank">here</a>
               to reset your Eco Credit password
            </div>
            """.formatted(token);
        var isHtml = true;

        sendEmail(email, subject, text, isHtml);
    }

    @Override
    public void sendPickupScheduledEmail(Pickup pickup) throws MessagingException {

        String subject = "Your pickup is scheduled for: " + pickup.getDateTime();
        String text = """
                   Dear
                   """ + pickup.getUser().getUsername() + """
                ,""" +
                """
                   <div>
                   This is to remind you that your pickup is scheduled for: 
                """ + pickup.getDateTime() +
                """
                        </div>
                        """;

        sendEmail(pickup.getUser().getEmail(), subject, text, true);
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
