package com.ncedu.cheetahtest.developer.entity;


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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getResetPassToken() {
        return resetPassToken;
    }

    public void setResetPassToken(int resetPassToken) {
        this.resetPassToken = resetPassToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", name='" + name + '\'' +
                ", type='" + role + '\'' +
                ", status='" + status + '\'' +
                ", resetPassToken='" + resetPassToken + '\'' +
                '}';
    }
}
