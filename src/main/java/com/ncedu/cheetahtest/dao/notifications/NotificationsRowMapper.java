package com.ncedu.cheetahtest.dao.notifications;

import com.ncedu.cheetahtest.entity.notification.NotificationStatus;
import com.ncedu.cheetahtest.entity.notification.ReadStatus;
import com.ncedu.cheetahtest.entity.notification.TestCaseNotification;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationsRowMapper implements RowMapper<TestCaseNotification> {
    public static final String ID = "id";
    public static final String NOTIFICATIONSTATUS = "notification_status";
    public static final String DATE = "date";
    public static final String TESTCASEID = "test_case_id";
    public static final String PROJECTID = "project_id";
    public static final String READSTATUS = "read_status";

    @Override
    public TestCaseNotification mapRow(ResultSet resultSet, int i) throws SQLException {
        TestCaseNotification testCaseNotification = new TestCaseNotification();
        testCaseNotification.setId(resultSet.getInt(ID));
        testCaseNotification.setNotificationStatus(NotificationStatus.valueOf(resultSet.getString(NOTIFICATIONSTATUS)));
        testCaseNotification.setDate(resultSet.getDate(DATE));
        testCaseNotification.setTestCaseId(resultSet.getInt(TESTCASEID));
        testCaseNotification.setProjectId(resultSet.getInt(PROJECTID));
        testCaseNotification.setReadStatus(ReadStatus.valueOf(resultSet.getString(READSTATUS)));

        return testCaseNotification;
    }
}
