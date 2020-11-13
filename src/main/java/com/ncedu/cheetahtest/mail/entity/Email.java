package com.ncedu.cheetahtest.mail.entity;

public class Email {

    private String emailAddress;

    @Override
    public String toString() {
        return "Email{" +
                "email='" + emailAddress + '\'' +
                '}';
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
