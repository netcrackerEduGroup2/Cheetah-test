package com.ncedu.cheetahtest.service.mail;

import java.util.List;

public interface EmailService {
    void sendSimpleMessage(String to, String text, String subject);

    void sendMessageWithAttachment(String to, String text, String subject, String htmlPath);

    void sendTestCaseReportToAddresses(List<String> emails, int idTestCase, int idProject);

    void sendGenerateTestCaseReportToAddresses(List<String> emails, int idTestCase, int idProject,
                                               int idHTC, String pathHTML);
}
