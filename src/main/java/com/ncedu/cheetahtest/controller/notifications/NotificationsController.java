package com.ncedu.cheetahtest.controller.notifications;

import com.ncedu.cheetahtest.entity.websocketdto.Message;
import com.ncedu.cheetahtest.service.notifications.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NotificationsController {
    private final NotificationService notificationService;


    @MessageMapping("/notifications")
    public void handleRequest(@Header("Authorization") String token, @Payload Message message, Principal principal) {
        log.info(message.toString());

        switch (message.getEvent()) {
            case "delete-notification":
                notificationService.deleteNotificationByMessage(message, token, principal);
                break;

            case "notifications-viewed":
                notificationService.editNotificationsStatusByMessage(message, token, principal);
                break;
            case "get-test-case-results-info":
                notificationService.sendProgressDetails(message, token, principal);
                break;
            default:
                notificationService.sendNotificationsToUserByMessage(token, principal);
                break;

        }
    }


}
