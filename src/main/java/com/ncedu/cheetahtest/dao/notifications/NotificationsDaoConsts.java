package com.ncedu.cheetahtest.dao.notifications;

public class NotificationsDaoConsts {
    public static final String CREATE_NOTIFICATION = "INSERT INTO notifications (user_id, notification_status, date, test_case_id, project_id, read_status,history_test_case_id) " +
            "VALUES (?,?::notification_status,?,?,?,?::read_notification_status,?)";
    public static final String EDIT_NOTIFICATION = "UPDATE notifications SET  notification_status = ?::notification_status, " +
            "test_case_id = ?,project_id = ? ,read_status =?::read_notification_status WHERE id = ?";
    public static final String DELETE_NOTIFICATION = "DELETE FROM notifications WHERE id = ?";
    public static final String GET_ALL_NOTIFICATIONS_BY_USER = "SELECT id, notification_status, date, test_case_id, project_id, read_status , history_test_case_id " +
            "FROM notifications WHERE user_id = ? ORDER BY read_status";
    public static final String GET_PAGINATED_NOTIFICATIONS = "SELECT id, notification_status, date, test_case_id, project_id, read_status ,history_test_case_id " +
            "FROM notifications WHERE user_id = ? ORDER BY read_status LIMIT ? OFFSET ?";
    public static final String FIND_BY_ID = "SELECT id, notification_status, date, test_case_id, project_id, read_status , history_test_case_id " +
            "FROM notifications WHERE id = ?";
    public static final String COUNT_NOTIFICATIONS = "SELECT count(*) FROM notifications WHERE user_id = ?";
    public static final String CHANGE_STATUS_BY_TEST_CASE_ID = "UPDATE notifications SET notification_status =?::notification_status WHERE test_case_id = ?";

}
