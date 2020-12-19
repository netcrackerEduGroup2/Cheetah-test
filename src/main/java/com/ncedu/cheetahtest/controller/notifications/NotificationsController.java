package com.ncedu.cheetahtest.controller.notifications;

import com.ncedu.cheetahtest.dao.notifications.NotificationStatusChangeDTO;
import com.ncedu.cheetahtest.entity.notification.ReadStatus;
import com.ncedu.cheetahtest.entity.websocketDTO.Message;
import com.ncedu.cheetahtest.service.notifications.TestCaseNotificationService;
import com.ncedu.cheetahtest.service.notifications.WebSocketNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NotificationsController {
    private final TestCaseNotificationService testCaseNotificationService;
    private final WebSocketNotificationService wsNotificationService;
    private final SimpMessagingTemplate smTemplate;


    @MessageMapping("/notifications")
    public void handleRequest(@Header("Authorization") String token, @Payload Message message, Principal principal) {
        log.info("\n\n___________MESSAGE RECEIVED____________");
        log.info(message.toString());
        log.info("\n_________________________________");
        switch (message.getEvent()) {
            case "delete-notification": {
                int id = (int) message.getData();
                this.deleteNotification(id);
                break;
            }
            case "notifications-viewed": {
                List<Integer> notifications = ((NotificationStatusChangeDTO) message.getData()).getNotifications();
                for (int notification : notifications) {
                    this.editNotificationStatus(notification);
                }
                break;
            }
            case "get-notifications": {
                Message messageToSend = this.getAllNotificationsByUserID(token, principal);
                smTemplate.convertAndSendToUser(principal.getName(), "/queue/notifications", messageToSend);
            }
        }
    }

    private Message getAllNotificationsByUserID(String token, Principal principal) {
        int idUser = parseTokenAndGetId(token);
        wsNotificationService.addConnection(idUser, principal.getName());
        Message messageToSend = new Message();
        messageToSend.setEvent("notifications");
        messageToSend.setData(testCaseNotificationService.getAllNotificationsByUserId(idUser));
        return messageToSend;
    }


    private void deleteNotification(int id) {
        testCaseNotificationService.deleteNotification(id);
    }

    private void editNotificationStatus(int id) {
        testCaseNotificationService.editReadStatus(ReadStatus.READ, id);
    }

    private int parseTokenAndGetId(String token) {
        String[] split_string = token.split("\\.");
        String body = split_string[1];
        Base64 base64Url = new Base64(true);
        body = new String(base64Url.decode(body));
        int idIndex = body.indexOf("\"id\":");
        return Integer.parseInt(body.substring(idIndex + 5, idIndex + 6));
    }

}
