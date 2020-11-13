package com.ncedu.cheetahtest.mail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService{

    public static final String NET_CRACKER_USERNAME = "spring.mail.username";
    public static final String SUBJECT = "Password reset";

    private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender emailSender;
    private final HtmlMail htmlMail;
    private final Environment environment;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender, HtmlMail htmlMail, Environment environment) {
        this.emailSender = emailSender;
        this.htmlMail = htmlMail;
        this.environment = environment;
    }

    @Override
    public void sendSimpleMessage(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Objects.requireNonNull(environment.getProperty(NET_CRACKER_USERNAME)));
        message.setTo(to);
        message.setSubject(SUBJECT);
        message.setText(text);

        emailSender.send(message);
    }

    @Override
    public void sendMessageWithAttachment(String to, String text) {

        MimeMessage message = emailSender.createMimeMessage();

        try {

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(Objects.requireNonNull(environment.getProperty(NET_CRACKER_USERNAME)));
            helper.setTo(to);
            helper.setSubject(SUBJECT);

            String htmlString = htmlMail.getHtmlWithStringInside(text)
                    .orElseThrow(FileNotFoundException::new);

            helper.setText("", htmlString);
            emailSender.send(message);

        } catch (MessagingException e) {
            logger.error(String.format("Couldn't send message: %s", e.getMessage()));
        } catch (FileNotFoundException e) {
            logger.error(String.format("Couldn't find html file by given path: %s", e.getMessage()));
        }
    }
}
