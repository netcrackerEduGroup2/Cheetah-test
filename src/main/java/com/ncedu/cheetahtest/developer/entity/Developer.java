package com.ncedu.cheetahtest.developer.entity;


public class Developer {

    private int id;
    private String login;
    private String pass;
    private String name;
    private String type;
    private String status;
    private int resetPassToken;

    public Developer() {
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
                ", login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", resetPassToken='" + resetPassToken + '\'' +
                '}';
    }
}
