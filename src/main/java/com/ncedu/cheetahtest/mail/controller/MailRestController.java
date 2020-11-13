package com.ncedu.cheetahtest.mail.controller;

import com.ncedu.cheetahtest.mail.exception.DeveloperNotFoundException;
import com.ncedu.cheetahtest.mail.model.Email;
import com.ncedu.cheetahtest.mail.service.EmailService;
import com.ncedu.cheetahtest.developer.entity.Developer;
import com.ncedu.cheetahtest.developer.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api")
public class MailRestController {

    private final EmailService emailService;
    private final DeveloperService developerService;

    @Autowired
    public MailRestController(EmailService emailService, DeveloperService developerService) {
        this.emailService = emailService;
        this.developerService = developerService;
    }

    @GetMapping("/reset-password")
    public Developer resetPassword(@RequestBody Email email) {
        Developer developer = developerService.findDeveloperByEmail(email.getEmailAddress());
        if (developer == null) {
            throw new DeveloperNotFoundException("Developer doesn't exist.");
        }

        String token = UUID.randomUUID().toString();

        developerService.createPasswordResetTokenForUser(developer, token);
        emailService.sendMessageWithAttachment(email.getEmailAddress(), constructUrl(token));

        return developer;
    }

    private String constructUrl(String token) {
        return "http://localhost:8080/api/change-password?token=" + token;
    }

    @GetMapping("/developers")
    public List<Developer> getDevelopers() {
        List<Developer> developers = developerService.getDevelopers();

        return developers;
    }
}
