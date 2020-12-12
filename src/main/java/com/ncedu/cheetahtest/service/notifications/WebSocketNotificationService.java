package com.ncedu.cheetahtest.service.notifications;

import com.ncedu.cheetahtest.entity.notification.TestCaseNotification;

import java.util.List;

public interface WebSocketNotificationService {
    void addConnection(int id,String name);

    void sendNotificationToUser(int idUser, List<TestCaseNotification> notifications);

}
