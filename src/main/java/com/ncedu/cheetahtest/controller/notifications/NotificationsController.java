package com.ncedu.cheetahtest.controller.notifications;

import com.ncedu.cheetahtest.entity.notification.NotificationResponse;
import com.ncedu.cheetahtest.entity.notification.TestCaseNotification;
import com.ncedu.cheetahtest.service.notifications.TestCaseNotificationService;
import com.ncedu.cheetahtest.service.notifications.WebSocketNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.Principal;
import java.util.List;

@Controller
@CrossOrigin(origins = "${frontend.ulr}")
public class NotificationsController {
    private final TestCaseNotificationService testCaseNotificationService;
    private final WebSocketNotificationService wsNotificationService;

    @Autowired
    public NotificationsController(TestCaseNotificationService testCaseNotificationService,
                                   WebSocketNotificationService wsNotificationService) {
        this.testCaseNotificationService = testCaseNotificationService;
        this.wsNotificationService = wsNotificationService;
    }


    @MessageMapping("/notifications")
    @SendToUser
    public List<TestCaseNotification> getAllNotificationsByUserID(@Header("Authorization") String token, Principal principal) {
        int idUser = parseTokenAndGetId(token);
        wsNotificationService.addConnection(idUser, principal.getName());
        return testCaseNotificationService.getAllNotificationsByUserId(idUser);
    }

    @MessageMapping("delete-notification")
    @SendToUser
    public NotificationResponse deleteNotification(int id) {
        testCaseNotificationService.deleteNotification(id);
        return new NotificationResponse("Success");
    }

    private int parseTokenAndGetId(String token) {
        String[] split_string = token.split("\\.");
        String body = split_string[1];
        int idIndex = body.indexOf("\"id\":");
        return Integer.parseInt(body.substring(idIndex + 5, idIndex + 6));
    }

}
