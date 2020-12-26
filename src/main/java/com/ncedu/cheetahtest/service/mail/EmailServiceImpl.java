package com.ncedu.cheetahtest.service.mail;


import com.ncedu.cheetahtest.entity.mail.SpecificReport;
import com.ncedu.cheetahtest.service.mail.reportsender.ReportSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService{

    static final String NET_CRACKER_USERNAME = "spring.mail.username";

    private final JavaMailSender emailSender;
    private final HtmlMail htmlMail;
    private final Environment environment;
    @Value("${frontend.ulr}")
    private String FRONT_URL;
    private ReportSender reportSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender, HtmlMail htmlMail, Environment environment,
                            ReportSender reportSender) {
        this.emailSender = emailSender;
        this.htmlMail = htmlMail;
        this.environment = environment;
        this.reportSender = reportSender;
    }

    @Override
    public void sendSimpleMessage(String to, String text, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Objects.requireNonNull(environment.getProperty(NET_CRACKER_USERNAME)));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
    }

    @Override
    public void sendMessageWithAttachment(String to, String text, String subject, String htmlPath) {

        MimeMessage message = emailSender.createMimeMessage();
        try {

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(Objects.requireNonNull(environment.getProperty(NET_CRACKER_USERNAME)));
            helper.setTo(to);
            helper.setSubject(subject);

            String htmlString = htmlMail.getHtmlWithStringInside(text, htmlPath)
                    .orElseThrow(FileNotFoundException::new);

            helper.setText("", htmlString);
            emailSender.send(message);

            log.info("Email has been sent to " + to);

        } catch (MessagingException e) {
            log.error(String.format( "Couldn't send message: %s", e.getMessage()));
        } catch (FileNotFoundException e) {
            log.error(String.format("Couldn't find html file by given path: %s", e.getMessage()));
        }
    }

    @Override
    public void sendTestCaseReportToAddresses(List<String> emails, int idTestCase, int idProject) {
        String url = FRONT_URL+"/projects/"+idProject+"/test-cases/"+idTestCase;
        String message = "Hi, testcase "+idTestCase+" was completed. \nTo see details, please follow the link:\n"+url;
        log.info(message);
        for ( String email: emails){
            sendSimpleMessage(email,message,"TestCaseInfo");
        }
    }

    @Override
    public void sendGenerateTestCaseReportToAddresses(List<String> emails, int idTestCase,
                                                      int idProject, int idHTC, String pathHTML) {
        for (String email: emails) {
            reportSender.sendReport(email, idTestCase, idProject, idHTC, pathHTML);
        }
    }
}
