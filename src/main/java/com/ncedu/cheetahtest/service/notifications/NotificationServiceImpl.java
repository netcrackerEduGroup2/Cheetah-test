package com.ncedu.cheetahtest.service.notifications;

import com.ncedu.cheetahtest.entity.notification.ReadStatus;
import com.ncedu.cheetahtest.entity.websocketdto.Message;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final WebSocketNotificationService wsNotificationService;
    private final SimpMessagingTemplate smTemplate;
    private final TestCaseNotificationService testCaseNotificationService;

    @Override
    public void sendProgressDetails(Message message, String token, Principal principal) {
        int idUser = parseTokenAndGetId(token);
        String username = principal.getName();
        int idTestCase = Integer.parseInt(message.getData().toString());
        wsNotificationService.addConnection(idUser, username);
        wsNotificationService.sendProgressOnDemand(username, idTestCase);
    }

    @Override
    public void deleteNotificationByMessage(Message message, String token, Principal principal) {
        int id = (int) message.getData();
        this.deleteNotification(id);
        sendMessageByPrincipal(token, principal);
    }

    @Override
    public void editNotificationsStatusByMessage(Message message, String token, Principal principal) {
        List<Integer> notifications = ((List<Integer>) message.getData());
        for (int notification : notifications) {
            this.editNotificationStatus(notification);
        }
        sendMessageByPrincipal(token, principal);
    }

    @Override
    public void sendMessageByPrincipal(String token, Principal principal) {
        Message messageToSend = getNotificationsAfterEdit(token);
        smTemplate.convertAndSendToUser(principal.getName(), "/queue/notifications", messageToSend);
    }

    @Override
    public void sendNotificationsToUserByMessage(String token, Principal principal) {
        Message messageToSend = this.getAllNotificationsByUserID(token, principal);
        smTemplate.convertAndSendToUser(principal.getName(), "/queue/notifications", messageToSend);
    }

    @Override
    public Message getAllNotificationsByUserID(String token, Principal principal) {
        int idUser = parseTokenAndGetId(token);
        wsNotificationService.addConnection(idUser, principal.getName());
        Message messageToSend = new Message();
        messageToSend.setEvent("notifications");
        messageToSend.setData(testCaseNotificationService.getAllNotificationsByUserId(idUser));
        return messageToSend;
    }

    @Override
    public Message getNotificationsAfterEdit(String token) {
        int idUser = parseTokenAndGetId(token);
        Message messageToSend = new Message();
        messageToSend.setEvent("notifications");
        messageToSend.setData(testCaseNotificationService.getAllNotificationsByUserId(idUser));
        return messageToSend;
    }

    @Override
    public int parseTokenAndGetId(String token) {
        String[] splitString = token.split("\\.");
        String body = splitString[1];
        org.apache.tomcat.util.codec.binary.Base64 base64Url = new Base64(true);
        body = new String(base64Url.decode(body));
        int idIndex = body.indexOf("\"id\":");
        return Integer.parseInt(body.substring(idIndex + 5, idIndex + 6));
    }

    private void deleteNotification(int id) {
        testCaseNotificationService.deleteNotification(id);
    }

    private void editNotificationStatus(int id) {
        testCaseNotificationService.editReadStatus(ReadStatus.READ, id);
    }
}
