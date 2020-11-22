package com.ncedu.cheetahtest.entity.user;

import lombok.Data;

@Data
public class User {

    private int id;
    private String email;
    private String pass;
    private String name;
    private String role;
    private String status;
    private int resetPassToken;


    public User(int id) {
        this.id = id;
    }

}
