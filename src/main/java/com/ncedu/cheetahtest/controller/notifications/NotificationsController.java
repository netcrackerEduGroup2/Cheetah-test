package com.ncedu.cheetahtest.controller.notifications;

import com.ncedu.cheetahtest.entity.notification.ReadStatus;
import com.ncedu.cheetahtest.entity.websocketdto.Message;
import com.ncedu.cheetahtest.service.notifications.NotificationService;
import com.ncedu.cheetahtest.service.notifications.TestCaseNotificationService;
import com.ncedu.cheetahtest.service.notifications.WebSocketNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NotificationsController {
    private final NotificationService notificationService;


    @MessageMapping("/notifications")
    public void handleRequest(@Header("Authorization") String token, @Payload Message message, Principal principal) {
        log.info("\n\n___________MESSAGE RECEIVED____________");
        log.info(message.toString());
        log.info("\n_________________________________");

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
