package com.ncedu.cheetahtest.controller.notifications;

import com.ncedu.cheetahtest.dao.notifications.NotificationStatusChangeDTO;
import com.ncedu.cheetahtest.entity.notification.ReadStatus;
import com.ncedu.cheetahtest.entity.websocketDTO.Message;
import com.ncedu.cheetahtest.service.notifications.TestCaseNotificationService;
import com.ncedu.cheetahtest.service.notifications.WebSocketNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.Principal;
import java.util.List;
@Controller
@CrossOrigin(origins = "${frontend.ulr}")
@RequiredArgsConstructor
public class NotificationsController {
    private final TestCaseNotificationService testCaseNotificationService;
    private final WebSocketNotificationService wsNotificationService;
    private final SimpMessagingTemplate smTemplate;


    @MessageMapping("/notifications")
    public void handleRequest(@Payload Message message) {
        System.out.println("\n\nENTERED METHOD HANDLE REQUEST IN MESSAGEMAPPING\n\n");
//        switch (message.getEvent()) {
//            case "delete-notification": {
//                int id = (int) message.getData();
//                this.deleteNotification(id);
//                break;
//            }
//            case "notifications-viewed":{
//                List<Integer> notifications = ((NotificationStatusChangeDTO)message.getData()).getNotifications();
//                for(int notification: notifications){
//                    this.editNotificationStatus(notification);
//                }
//                break;
//            }
//            case "get-notifications":{
//                Message messageToSend = this.getAllNotificationsByUserID(token,principal);
//                smTemplate.convertAndSendToUser(principal.getName(),"/notifications",messageToSend);
//            }
//        }
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
        int idIndex = body.indexOf("\"id\":");
        return Integer.parseInt(body.substring(idIndex + 5, idIndex + 6));
    }

}
