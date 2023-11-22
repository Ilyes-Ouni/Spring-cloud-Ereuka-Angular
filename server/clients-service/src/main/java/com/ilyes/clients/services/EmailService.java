package com.ilyes.clients.services;

// Importing required classes
import com.ilyes.clients.entities.EmailDetails;

// Interface
public interface EmailService {
    // To send a simple email
    String sendSimpleMail(EmailDetails details);
}

