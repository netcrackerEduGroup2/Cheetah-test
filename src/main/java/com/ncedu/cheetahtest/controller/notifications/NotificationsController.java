package com.ncedu.cheetahtest.controller.notifications;

import com.ncedu.cheetahtest.entity.notification.ReadStatus;
import com.ncedu.cheetahtest.entity.websocketdto.Message;
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
    private final TestCaseNotificationService testCaseNotificationService;
    private final WebSocketNotificationService wsNotificationService;
    private final SimpMessagingTemplate smTemplate;


    @MessageMapping("/notifications")
    public void handleRequest(@Header("Authorization") String token, @Payload Message message, Principal principal) {
        log.info("\n\n___________MESSAGE RECEIVED____________");
        log.info(message.toString());
        log.info("\n_________________________________");

        switch (message.getEvent()) {
            case "delete-notification":
                this.deleteNotificationByMessage(message, token, principal);
                break;

            case "notifications-viewed":
                this.editNotificationsStatusByMessage(message, token, principal);
                break;
            case "get-test-case-results-info":
                this.sendProgressDetails(message, token, principal);
                break;
            default:
                this.sendNotificationsToUserByMessage(token, principal);
                break;

        }
    }
    private void sendProgressDetails(Message message, String token, Principal principal){
        int idUser = parseTokenAndGetId(token);
        String username  = principal.getName();
        int idTestCase = Integer.parseInt(message.getData().toString());
        wsNotificationService.addConnection(idUser,username);
        wsNotificationService.sendProgressOnDemand(username, idTestCase);
    }

    private void deleteNotificationByMessage(Message message, String token, Principal principal) {
        int id = (int) message.getData();
        this.deleteNotification(id);
        sendMessageByPrincipal(token, principal);

    }

    private void editNotificationsStatusByMessage(Message message, String token, Principal principal) {
        List<Integer> notifications = ((List<Integer>) message.getData());
        for (int notification : notifications) {
            this.editNotificationStatus(notification);
        }
        sendMessageByPrincipal(token, principal);

    }

    private void sendMessageByPrincipal(String token, Principal principal) {
        Message messageToSend = getNotificationsAfterEdit(token);
        smTemplate.convertAndSendToUser(principal.getName(), "/queue/notifications", messageToSend);
    }

    private void sendNotificationsToUserByMessage(String token, Principal principal) {
        Message messageToSend = this.getAllNotificationsByUserID(token, principal);
        smTemplate.convertAndSendToUser(principal.getName(), "/queue/notifications", messageToSend);
    }

    private Message getAllNotificationsByUserID(String token, Principal principal) {
        int idUser = parseTokenAndGetId(token);
        wsNotificationService.addConnection(idUser, principal.getName());
        Message messageToSend = new Message();
        messageToSend.setEvent("notifications");
        messageToSend.setData(testCaseNotificationService.getAllNotificationsByUserId(idUser));
        return messageToSend;
    }

    private Message getNotificationsAfterEdit(String token) {
        int idUser = parseTokenAndGetId(token);
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
        String[] splitString = token.split("\\.");
        String body = splitString[1];
        Base64 base64Url = new Base64(true);
        body = new String(base64Url.decode(body));
        int idIndex = body.indexOf("\"id\":");
        return Integer.parseInt(body.substring(idIndex + 5, idIndex + 6));
    }

}
