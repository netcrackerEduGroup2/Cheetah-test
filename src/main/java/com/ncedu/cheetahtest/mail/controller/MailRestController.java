package com.ncedu.cheetahtest.mail.controller;

import com.ncedu.cheetahtest.developer.entity.ResetToken;
import com.ncedu.cheetahtest.mail.entity.Email;
import com.ncedu.cheetahtest.mail.service.EmailService;
import com.ncedu.cheetahtest.developer.entity.Developer;
import com.ncedu.cheetahtest.developer.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


@RestController
@RequestMapping("/api")
public class MailRestController {

    public static final String FRONT_URL = "http://localhost:8080/api/change-password?token=";
    private final EmailService emailService;
    private final DeveloperService developerService;

    @Autowired
    public MailRestController(EmailService emailService, DeveloperService developerService) {
        this.emailService = emailService;
        this.developerService = developerService;
    }

    @GetMapping("/reset-password")
    public Developer resetPassword(@RequestBody Email email,
                                   HttpServletResponse response) {
        Developer developer = developerService.findDeveloperByEmail(email.getEmailAddress());
        if (developer == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        String token = UUID.randomUUID().toString();

        developerService.createPasswordResetTokenForUser(developer, token);
        emailService.sendMessageWithAttachment(email.getEmailAddress(), constructUrl(token));

        return developer;
    }

    @GetMapping("/change-password")
    public Developer changePassword(@RequestParam("token") String token) {
        String result = validatePasswordResetToken(token);
        if (result == null) {
            //TODO
        } else {
            //TODO
        }
        return null;
    }

    private String constructUrl(String token) {
        return FRONT_URL + token;
    }


    public String validatePasswordResetToken(String token) {
        final ResetToken passToken = developerService.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;

    }

    private boolean isTokenFound(ResetToken token) {
        return token != null;
    }

    private boolean isTokenExpired(ResetToken passToken) {
        Calendar calendarExpiry = Calendar.getInstance();
        calendarExpiry.setTime(passToken.getExpiryDate());

        Calendar calendarCurrent = Calendar.getInstance();
        calendarCurrent.setTime(new Date());

        return calendarExpiry.before(calendarCurrent);
    }

}
