package com.ilyes.clients.services;

import com.ilyes.clients.entities.EmailDetails;
import com.ilyes.clients.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("yassine.ouni007@gmail.com")
    private String senderEmail;

    @Value("nkjqzclqpszqpvtw")
    private String senderPassword;

    public String sendSimpleMail(EmailDetails details) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(senderEmail);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText("Your verification code is: " + details.getMsgBody());
            mailMessage.setSubject("Verify your Ouni's account");

            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
}
