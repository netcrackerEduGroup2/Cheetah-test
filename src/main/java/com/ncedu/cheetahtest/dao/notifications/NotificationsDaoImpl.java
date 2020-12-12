package com.ncedu.cheetahtest.dao.notifications;

import com.ncedu.cheetahtest.entity.notification.TestCaseNotification;
import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository

public class NotificationsDaoImpl implements NotificationsDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NotificationsDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }

    @Override
    public void createNotification(TestCaseNotification testCaseNotification) {
        String sql = "INSERT INTO notifications (user_id, notification_status, date, test_case_id, project_id, read_status) " +
                "VALUES (?,?::notification_status,?,?,?,?::read_notification_status)";
        jdbcTemplate.update(
                sql,
                testCaseNotification.getUserId(),
                testCaseNotification.getNotificationStatus().toString(),
                testCaseNotification.getDate(),
                testCaseNotification.getTestCaseId(),
                testCaseNotification.getProjectId(),
                testCaseNotification.getReadStatus().toString()
        );
    }

    @Override
    public TestCaseNotification editNotification(TestCaseNotification testCaseNotification, int id) {
        String sql = "UPDATE notifications SET user_id = ?, notification_status = ?::notification_status, " +
                "test_case_id = ?,project_id = ? ,read_status =?::read_notification_status WHERE id = ?";
        jdbcTemplate.update(
                sql,
                testCaseNotification.getUserId(),
                testCaseNotification.getNotificationStatus().toString(),
                testCaseNotification.getTestCaseId(),
                testCaseNotification.getProjectId(),
                testCaseNotification.getReadStatus().toString(),
                id
        );
        return this.findById(id);
    }

    @Override
    public void deleteNotification(int id) {
        String sql = "DELETE FROM notifications WHERE id = ?";
        jdbcTemplate.update(
                sql,
                id
        );
    }

    @Override
    public List<TestCaseNotification> getAllNotificationsByUserId(int userId) {
        String sql = "SELECT id, notification_status, date, test_case_id, project_id, read_status " +
                "FROM notifications WHERE user_id = ? ORDER BY read_status";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, userId),
                new NotificationsRowMapper()
        );
    }

    @Override
    public List<TestCaseNotification> getNotificationsByUserIdPaginated(int userId, int limit, int offset) {
        String sql = "SELECT id, notification_status, date, test_case_id, project_id, read_status " +
                "FROM notifications WHERE user_id = ? ORDER BY read_status LIMIT ? OFFSET ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setInt(1, userId);
                    preparedStatement.setInt(2, limit);
                    preparedStatement.setInt(3, offset);
                },
                new NotificationsRowMapper()
        );
    }

    @Override
    public TestCaseNotification findById(int id) {
        String sql = "SELECT id, notification_status, date, test_case_id, project_id, read_status " +
                "FROM notifications WHERE id = ?";
        List<TestCaseNotification> testCaseNotifications = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new NotificationsRowMapper()
        );
        if (testCaseNotifications.size() == 1) {
            return testCaseNotifications.get(0);
        } else return null;
    }

    @Override
    public int countNotificationsByUserId(int userId) {
        String sql = "SELECT count(*) FROM notifications WHERE user_id = ?";
        List<Integer> counts = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, userId),
                new NotificationsCountRowMapper());
        if (counts.size() == 1) {
            return counts.get(0);
        } else return 0;
    }

    @Override
    public void changeStatusByTestCaseId(int idTestCase, TestCaseResult testCaseResult) {
        String sql = "UPDATE notifications SET notification_status =?::notification_status WHERE test_case_id = ?";
        jdbcTemplate.update(
                sql,
                testCaseResult.toString(),
                idTestCase
        );
    }
}
