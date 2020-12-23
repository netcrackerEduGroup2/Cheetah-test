package com.ncedu.cheetahtest.service.notifications;

import com.ncedu.cheetahtest.entity.websocketdto.Message;

import java.security.Principal;

public interface NotificationService {
    void sendProgressDetails(Message message, String token, Principal principal);

    void deleteNotificationByMessage(Message message, String token, Principal principal);

    void editNotificationsStatusByMessage(Message message, String token, Principal principal);

    void sendMessageByPrincipal(String token, Principal principal);

    void sendNotificationsToUserByMessage(String token, Principal principal);

    Message getAllNotificationsByUserID(String token, Principal principal);

    Message getNotificationsAfterEdit(String token);

    int parseTokenAndGetId(String token);
}
