package com.ilyes.microsservice.restControllers;

import com.ilyes.microsservice.config.MailStructure;
import com.ilyes.microsservice.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class MailController {
    @Autowired
    private MailService mailService;

    @PostMapping("/send/{email}")
    public String sendMail (@PathVariable String email, @RequestBody MailStructure mailStructure) {
        mailService.sendMail(email, mailStructure);
        return "Mail sent successfully";
    }
}
