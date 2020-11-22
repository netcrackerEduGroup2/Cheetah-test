package com.ncedu.cheetahtest.dao.user;

public class UserConsts {
    public static final String CREATE_DEVELOPER = "INSERT INTO users (email, password, name, role, status) VALUES (?,?,?,?::user_role,?::user_status)";
    public static final String FIND_USER_BY_EMAIL = "SELECT id, email, password, name, role, status, reset_token_id, last_request FROM users WHERE email = ? LIMIT  1";
    public static final String FIND_USER_BY_EMAIL_AND_PASSWORD = "SELECT id, email, password, name, role, status, reset_token_id, last_request FROM users WHERE email = ? AND password = ? LIMIT 1";
    public static final String CHANGE_USER_PASSWORD = "UPDATE users SET password = ? WHERE id = ?";
    public static final String FIND_USER_BY_TOKEN = "SELECT id, email, password, name, role, status, reset_token_id, last_request FROM users WHERE id IN (SELECT user_id FROM reset_token WHERE token = ?) LIMIT 1";
    public static final String SET_USER_LAST_REQUEST = "UPDATE users SET last_request = ? WHERE email = ?";
}
