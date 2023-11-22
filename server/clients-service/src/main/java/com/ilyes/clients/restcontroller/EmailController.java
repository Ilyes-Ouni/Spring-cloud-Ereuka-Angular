package com.ilyes.clients.restcontroller;

// Importing required classes

import com.ilyes.clients.services.EmailService;
import com.ilyes.clients.entities.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Annotation
@RestController
// Class
public class EmailController {

    @Autowired private EmailService emailService;


    // Sending a simple Email
    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details) {
        String status = emailService.sendSimpleMail(details);
        return status;
    }
}
