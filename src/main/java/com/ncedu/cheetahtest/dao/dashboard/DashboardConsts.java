package com.ncedu.cheetahtest.dao.dashboard;

public class DashboardConsts {
    public static final String COUNT_ACTIVE_USER_BY_ROLE_SQL = "SELECT count(*) FROM users WHERE role::text LIKE ? AND status='ACTIVE';";
    public static final String GET_ALL_USER_ACTIVITY_SQL ="SELECT name, role, last_request, photo_url FROM users WHERE last_request > current_timestamp - ?::INTERVAL ORDER BY last_request DESC ;";
    public static final String GET_USER_ACTIVITY_FOR_MANAGER_SQL ="SELECT name, role, last_request, photo_url FROM users WHERE last_request > current_timestamp - ?::INTERVAL AND role <> 'ADMIN' ORDER BY last_request DESC ;";
    public static final String COUNT_PROJECTS_CREATED_PER_DAY_ON_WEEK ="SELECT count(*) FROM project WHERE create_date > current_timestamp - ?::INTERVAL AND create_date <= current_timestamp - ?::INTERVAL;";
    public static final String COUNT_ALL_PROJECT_SQL = "SELECT count(*) from project;";
    public static final String COUNT_PROJECT_PER_WEEK_SQL = "SELECT count(*) FROM project WHERE create_date > current_timestamp - ?::INTERVAL;";
    public static final String GET_PROJECTS_FOR_USER_SQL ="SELECT p.id, p.title, u.user_status FROM user_project u INNER JOIN project p on p.id = u.project_id WHERE u.user_id = ? AND (u.user_status = 'WATCHER' OR u.user_status = 'DEVELOPER') AND p.status = 'ACTIVE' ORDER BY p.create_date;";
    public static final String COUNT_ARCHIVED_PROJECTS_SQL = "SELECT count(*) FROM project WHERE status = 'INACTIVE'";
}
