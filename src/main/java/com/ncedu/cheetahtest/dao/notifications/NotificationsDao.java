package com.ncedu.cheetahtest.dao.notifications;

import com.ncedu.cheetahtest.entity.notification.TestCaseNotification;

import java.util.List;

public interface NotificationsDao {
    void createNotification(TestCaseNotification testCaseNotification);

    TestCaseNotification editNotification(TestCaseNotification testCaseNotification, int id);

    void deleteNotification(int id);

    List<TestCaseNotification> getAllNotificationsByUserId (int userId);

    List<TestCaseNotification> getNotificationsByUserIdPaginated(int userId, int limit, int offset);

    TestCaseNotification findById(int id);

}
