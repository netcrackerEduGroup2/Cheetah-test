package com.ncedu.cheetahtest.mail.service;

public interface EmailService {
    void sendSimpleMessage(String to, String text);

    void sendMessageWithAttachment(String to, String text);
}
