package com.ncedu.cheetahtest.dao.notifications;

import com.ncedu.cheetahtest.entity.notification.TestCaseNotification;
import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.ncedu.cheetahtest.dao.notifications.NotificationsDaoConsts.*;


@Repository

public class NotificationsDaoImpl implements NotificationsDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NotificationsDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }

    @Override
    public void createNotification(TestCaseNotification testCaseNotification) {
        jdbcTemplate.update(
                CREATE_NOTIFICATION,
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
        jdbcTemplate.update(
                EDIT_NOTIFICATION,
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
        jdbcTemplate.update(
                DELETE_NOTIFICATION,
                id
        );
    }

    @Override
    public List<TestCaseNotification> getAllNotificationsByUserId(int userId) {
        return jdbcTemplate.query(
                GET_ALL_NOTIFICATIONS_BY_USER,
                preparedStatement -> preparedStatement.setInt(1, userId),
                new NotificationsRowMapper()
        );
    }

    @Override
    public List<TestCaseNotification> getNotificationsByUserIdPaginated(int userId, int limit, int offset) {
        return jdbcTemplate.query(
                GET_PAGINATED_NOTIFICATIONS,
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
        List<TestCaseNotification> testCaseNotifications = jdbcTemplate.query(
                FIND_BY_ID,
                preparedStatement -> preparedStatement.setInt(1, id),
                new NotificationsRowMapper()
        );
        if (testCaseNotifications.size() == 1) {
            return testCaseNotifications.get(0);
        } else return null;
    }

    @Override
    public int countNotificationsByUserId(int userId) {
        List<Integer> counts = jdbcTemplate.query(
                COUNT_NOTIFICATIONS,
                preparedStatement -> preparedStatement.setInt(1, userId),
                new NotificationsCountRowMapper());
        if (counts.size() == 1) {
            return counts.get(0);
        } else return 0;
    }

    @Override
    public void changeStatusByTestCaseId(int idTestCase, TestCaseResult testCaseResult) {
        jdbcTemplate.update(
                CHANGE_STATUS_BY_TEST_CASE_ID,
                testCaseResult.toString(),
                idTestCase
        );
    }
}
