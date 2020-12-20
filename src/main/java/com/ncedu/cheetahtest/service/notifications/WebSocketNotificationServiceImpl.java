package com.ncedu.cheetahtest.service.notifications;

import com.ncedu.cheetahtest.entity.notification.TestCaseNotification;
import com.ncedu.cheetahtest.entity.progress.TestCaseProgressReport;
import com.ncedu.cheetahtest.entity.websocketdto.Message;
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
        message.setEvent("notifications");
        message.setData(notifications);
        if(username!=null){
            simpMessagingTemplate.convertAndSendToUser(username, "/queue/notifications", message);
        }

    }

    @Override
    public void sendProgressToUser(int idUser, TestCaseProgressReport testCaseProgressReport) {
        String username = connections.get(idUser);
        Message message = new Message();
        message.setEvent("test-case-execution-actions");
        message.setData(testCaseProgressReport);
        if(username!=null){
            simpMessagingTemplate.convertAndSendToUser(username, "/queue/notifications", message);
        }
    }
}
