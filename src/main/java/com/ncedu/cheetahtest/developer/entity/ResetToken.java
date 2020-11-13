package com.ncedu.cheetahtest.developer.entity;


import java.util.Calendar;
import java.util.Date;

public class ResetToken {
    private static final int EXPIRATION = 3600 * 24 * 1000;

    private int id;

    private String token;

    private int developerId;

    private Date expiryDate;

    public ResetToken() {
    }

    public ResetToken(String token, int developerId, Date expiryDate) {
        this.token = token;
        this.developerId = developerId;
        this.expiryDate = expiryDate;
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

    public int getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(int developerId) {
        this.developerId = developerId;
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

    @Override
    public String toString() {
        return "ResetToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", developerId=" + developerId +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
