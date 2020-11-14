package com.ncedu.cheetahtest.developer.entity;

import lombok.Data;

@Data
public class Developer {

    private int id;
    private String email;
    private String pass;
    private String name;
    private String role;
    private String status;
    private int resetPassToken;

    public Developer() {
    }

    public Developer(int id, String email, String pass, String name, String type, String status, int resetPassToken) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.name = name;
        this.role = type;
        this.status = status;
        this.resetPassToken = resetPassToken;
    }

    public Developer(int id) {
        this.id = id;
    }

}
