package com.ncedu.cheetahtest.dao.user;

public class UserConsts {
    public static final String CREATE_DEVELOPER_SQL = "INSERT INTO users (email, password, name, role, status) VALUES (?,?,?,?::user_role,?::user_status)";
    public static final String FIND_USER_BY_EMAIL_SQL = "SELECT id, email, password, name, role, status, last_request, photo_url FROM users WHERE email = ? LIMIT 1";
    public static final String FIND_USER_BY_EMAIL_AND_PASSWORD_SQL = "SELECT id, email, password, name, role, status, last_request, photo_url FROM users WHERE email = ? AND password = ? LIMIT 1";
    public static final String CHANGE_USER_PASSWORD_SQL = "UPDATE users SET password = ? WHERE id = ?";
    public static final String FIND_USER_BY_TOKEN_SQL = "SELECT id, email, password, name, role, status, last_request, photo_url FROM users WHERE id IN (SELECT user_id FROM reset_token WHERE token = ?) LIMIT 1";
    public static final String SET_USER_LAST_REQUEST_SQL = "UPDATE users SET last_request = ? WHERE email = ?";
    public static final String EDIT_USER_SQL = "UPDATE users SET email = ?, name = ?, role = ?::user_role where id = ?";
    public static final String CHANGE_USER_STATUS_SQL = "UPDATE users SET status = ?::user_status where id = ?";
    public static final String SET_USER_PHOTO_URL_SQL = "UPDATE users SET photo_url = ? WHERE id = ?";
    public static final String FIND_USER_BY_ID_SQL = "SELECT id, email, password, name, role, status, last_request, photo_url FROM users WHERE id = ? LIMIT 1";
    public static final String FIND_ALL_ACTIVE_USERS_SQL = "SELECT id, email, password, name, role, status, last_request, photo_url FROM users WHERE status='ACTIVE';";
    public static final String FIND_USER_BY_EMAIL_NAME_ROLE_SQL = "SELECT * FROM users WHERE email LIKE ? AND name LIKE ? AND role::text LIKE ? AND status='ACTIVE' LIMIT ? OFFSET ?;";
    public static final String COUNT_USER_BY_EMAIL_NAME_ROLE_SQL = "SELECT count(*) FROM users WHERE email LIKE ? AND name LIKE ? AND role::text LIKE ? AND status='ACTIVE';";
    public static final String FIND_BY_EMAIL = "SELECT id, email,name,role,status FROM users WHERE email LIKE CONCAT ('%',?,'%') ORDER BY email LIMIT 5";
    public static final String GET_USERS_BY_PROJECT_ID = "SELECT users.id FROM users INNER JOIN user_project up on users.id = up.user_id " +
            "INNER JOIN project p on p.id = up.project_id WHERE p.id = ?";
    public static final String FIND_WATCHERS_BY_PROJECT_ID = "SELECT id, email, name, role, status FROM users WHERE id IN (SELECT user_id FROM user_project WHERE project_id = ? AND user_status = 'WATCHER')";
    public static final String REMOVE_WATCHERS_FROM_PROJECT = "DELETE FROM user_project WHERE project_id = ? AND user_status = 'WATCHER'";
    public static final String CREATE_WATCHER_SQL = "INSERT INTO user_project(project_id, user_id, user_status) VALUES (?, ?, 'WATCHER')";
}
