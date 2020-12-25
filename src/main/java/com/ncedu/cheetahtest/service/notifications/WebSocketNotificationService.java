package com.ncedu.cheetahtest.service.notifications;

import com.ncedu.cheetahtest.entity.notification.TestCaseNotification;
import com.ncedu.cheetahtest.entity.progress.TestCaseProgressReport;

import java.util.List;

public interface WebSocketNotificationService {
    void addConnection(int id, String name);

    void sendNotificationToUser(int idUser, List<TestCaseNotification> notifications);

    void sendProgressToUser(int idUser, TestCaseProgressReport testCaseProgressReport);

    void sendProgressOnDemand(String username, int idTestCase);

    void sendProgressToAllUsers(TestCaseProgressReport testCaseProgressReport);

}
