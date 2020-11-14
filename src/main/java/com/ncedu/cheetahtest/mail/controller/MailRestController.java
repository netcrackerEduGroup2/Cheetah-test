package com.ncedu.cheetahtest.mail.controller;

import com.ncedu.cheetahtest.developer.entity.ResetToken;
import com.ncedu.cheetahtest.mail.entity.Email;
import com.ncedu.cheetahtest.mail.entity.GenericResponse;
import com.ncedu.cheetahtest.mail.entity.PasswordDTO;
import com.ncedu.cheetahtest.mail.service.EmailService;
import com.ncedu.cheetahtest.developer.entity.Developer;
import com.ncedu.cheetahtest.developer.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<GenericResponse> resetPassword(@RequestBody Email email) {
        Developer developer = developerService.findDeveloperByEmail(email.getEmailAddress());
        if (developer == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String token = UUID.randomUUID().toString();

        developerService.createPasswordResetTokenForUser(developer, token);
        emailService.sendMessageWithAttachment(email.getEmailAddress(), constructUrl(token));

        return new ResponseEntity<>(new GenericResponse("user.fetched"), HttpStatus.OK);
    }

    @GetMapping("/change-password")
    public ResponseEntity<GenericResponse> changePassword(@RequestParam("token") String token,
                                          HttpServletResponse response,
                                                          @RequestBody PasswordDTO passwordDTO) {

        String result = validatePasswordResetToken(token);
        if (result != null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ResponseEntity<>(new GenericResponse(result), HttpStatus.BAD_REQUEST);
        }

        boolean isPasswordSame = developerService.validatePassword(passwordDTO, token);
        if (isPasswordSame) {
            return new ResponseEntity<>(new GenericResponse("same.password"), HttpStatus.BAD_REQUEST);
        }

        passwordDTO.setToken(token);
        ResetToken resetToken = developerService.findByToken(passwordDTO.getToken());

        if (resetToken != null) {
            developerService.changeUserPassword(resetToken, passwordDTO.getPassword());
            return new ResponseEntity<>(new GenericResponse("message.resetPasswordSuc"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GenericResponse("reset.token.null"), HttpStatus.BAD_REQUEST);
        }
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
