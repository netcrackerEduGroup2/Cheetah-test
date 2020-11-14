package com.ncedu.cheetahtest.mail.entity;

public class PasswordDTO {
    private  String token;

    private String password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PasswordDTO(String token, String password) {
        this.token = token;
        this.password = password;
    }

    @Override
    public String toString() {
        return "PasswordDTO{" +
                "token='" + token + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

