package com.ncedu.cheetahtest.service.notifications;

import com.ncedu.cheetahtest.entity.notification.TestCaseNotification;
import com.ncedu.cheetahtest.entity.websocketDTO.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WebSocketNotificationServiceImpl implements WebSocketNotificationService {
    private Map<Integer, String> connections;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired

    public WebSocketNotificationServiceImpl(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.connections = new HashMap<>();
    }

    @Override
    public void addConnection(int id, String name) {
        connections.put(id, name);
    }

    @Override
    public void sendNotificationToUser(int idUser, List<TestCaseNotification> notifications) {
        String username = connections.get(idUser);
        Message message = new Message();
        message.setMessageType("NOTIFICATIONS");
        message.setPayload(notifications);
        simpMessagingTemplate.convertAndSendToUser(username, "/notifications", message);
    }
}
