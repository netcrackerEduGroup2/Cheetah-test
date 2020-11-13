package com.ncedu.cheetahtest.developer.entity;

import java.util.Date;

public class ResetToken {
    private static final int EXPIRATION = 60 * 24;

    private int id;

    private String token;

    private Developer developer;

    private Date expiryDate;

    public ResetToken(String token, Developer developer) {
        this.token = token;
        this.developer = developer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }
}
