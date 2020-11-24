package com.ncedu.cheetahtest.entity.user;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private int id;
    private String email;
    private String pass;
    private String name;
    private String role;
    private String status;
    private int resetPassToken;
    private Date lastRequest;

    public User() {

    }

    public User(int id, String email, String pass, String name, String role, String status, int resetPassToken, Date lastRequest) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.name = name;
        this.role = role;
        this.status = status;
        this.resetPassToken = resetPassToken;
        this.lastRequest = lastRequest;
    }

    public User(int id) {
        this.id = id;
    }

}
