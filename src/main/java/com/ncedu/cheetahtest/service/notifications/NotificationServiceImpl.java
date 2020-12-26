package com.ncedu.cheetahtest.service.notifications;

import com.ncedu.cheetahtest.dao.notifications.NotificationsDao;
import com.ncedu.cheetahtest.dao.project.ProjectDao;
import com.ncedu.cheetahtest.dao.user.UserDao;
import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCaseFull;
import com.ncedu.cheetahtest.entity.notification.NotificationStatus;
import com.ncedu.cheetahtest.entity.notification.ReadStatus;
import com.ncedu.cheetahtest.entity.notification.TestCaseNotification;
import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;
import com.ncedu.cheetahtest.entity.websocketdto.Message;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final WebSocketNotificationService wsNotificationService;
    private final SimpMessagingTemplate smTemplate;
    private final TestCaseNotificationService testCaseNotificationService;
    private final ProjectDao projectDao;
    private final UserDao userDao;
    private final NotificationsDao notificationsDao;

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
        List<Integer> notifications = (List<Integer>) message.getData();
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
        List<TestCaseNotification> testCaseNotifications = testCaseNotificationService.getAllNotificationsByUserId(idUser);
        messageToSend.setData(this.addTitleAndDescription(testCaseNotifications));
        return messageToSend;
    }

    @Override
    public Message getNotificationsAfterEdit(String token) {
        int idUser = parseTokenAndGetId(token);
        Message messageToSend = new Message();
        messageToSend.setEvent("notifications");
        messageToSend.setData(this.addTitleAndDescription(testCaseNotificationService.getAllNotificationsByUserId(idUser)));
        return messageToSend;
    }

    @Override
    public int parseTokenAndGetId(String token) {
        String[] splitString = token.split("\\.");
        String body = splitString[1];
        org.apache.tomcat.util.codec.binary.Base64 base64Url = new Base64(true);
        body = new String(base64Url.decode(body));
        splitString = body.split(",");
        for(String row : splitString){
            if(row.contains("\"id\":")){
                body = row;
                break;
            }
        }
        String id = body.replaceFirst("\"id\":","");
        return Integer.parseInt(id);
    }

    @Override
    @Async
    public void notifyAboutTestCaseExecution(HistoryTestCaseFull historyTestCase) {
        TestCaseNotification testCaseNotification = new TestCaseNotification();
        if (historyTestCase.getResult() == TestCaseResult.COMPLETE) {
            testCaseNotification.setNotificationStatus(NotificationStatus.COMPLETE);
        } else if (historyTestCase.getResult() == TestCaseResult.FAILED) {
            testCaseNotification.setNotificationStatus(NotificationStatus.FAILED);
        } else {
            testCaseNotification.setNotificationStatus(NotificationStatus.INPROCESS);
        }

        int projectId = projectDao.findProjectByTestCaseId(historyTestCase.getIdTestCase()).getId();
        testCaseNotification.setProjectId(projectId);
        testCaseNotification.setTestCaseId(historyTestCase.getIdTestCase());
        testCaseNotification.setHtcId(historyTestCase.getId());
        testCaseNotification.setReadStatus(ReadStatus.UNREAD);

        List<Integer> userIds = userDao.getUsersIdByProjectId(projectId);

        for (int userId : userIds) {
            testCaseNotification.setUserId(userId);
            notificationsDao.createNotification(testCaseNotification);
        }
        sendNotificationsToUsers(userIds);
    }

    @Override
    @Async
    public void notifyAboutTestCaseStatusChange(HistoryTestCaseFull historyTestCase, TestCaseResult testCaseResult) {
        int projectId = projectDao.findProjectByTestCaseId(historyTestCase.getIdTestCase()).getId();
        notificationsDao.changeStatusByTestCaseId(historyTestCase.getIdTestCase(), testCaseResult);
        List<Integer> userIds = userDao.getUsersIdByProjectId(projectId);
        sendNotificationsToUsers(userIds);

    }

    private void sendNotificationsToUsers(List<Integer> userIds) {
        for (int userId : userIds) {
            List<TestCaseNotification> notifications = testCaseNotificationService.getAllNotificationsByUserId(userId);
            wsNotificationService.sendNotificationToUser(userId, addTitleAndDescription(notifications));

        }
    }
    private List<TestCaseNotification> addTitleAndDescription(
            List<TestCaseNotification> testCaseNotifications){
        final String TESTCASE= "Test case ";
        final String FOLLOWLINK = "For more detailed information please, follow the link below:";
        final String INPROJECT = " in project ";

        for (TestCaseNotification testCaseNotification: testCaseNotifications){
            if(testCaseNotification.getNotificationStatus() == NotificationStatus.COMPLETE){
                testCaseNotification.setTitle(TESTCASE+testCaseNotification.getTestCaseId()+
                        INPROJECT+testCaseNotification.getProjectId()+" successfully ended");
                testCaseNotification.setDescription(TESTCASE+testCaseNotification.getTestCaseId()+
                        INPROJECT+testCaseNotification.getProjectId()+" successfully ended.\n " +
                        FOLLOWLINK
                );
            }
            else if(testCaseNotification.getNotificationStatus() == NotificationStatus.FAILED){
                testCaseNotification.setTitle(TESTCASE+testCaseNotification.getTestCaseId()+
                        INPROJECT+testCaseNotification.getProjectId()+" failed");
                testCaseNotification.setDescription(TESTCASE+testCaseNotification.getTestCaseId()+
                        INPROJECT+testCaseNotification.getProjectId()+" failed.\n " +
                        FOLLOWLINK);
            }
            else{
                testCaseNotification.setTitle(TESTCASE+testCaseNotification.getTestCaseId()+
                        INPROJECT+testCaseNotification.getProjectId()+" is in progress of executing");
                testCaseNotification.setDescription(TESTCASE+testCaseNotification.getTestCaseId()+
                        INPROJECT+testCaseNotification.getProjectId()+" is in progress of executing.\n " +
                        FOLLOWLINK);
            }
        }
        return testCaseNotifications;
    }
    private void deleteNotification(int id) {
        testCaseNotificationService.deleteNotification(id);
    }

    private void editNotificationStatus(int id) {
        testCaseNotificationService.editReadStatus(ReadStatus.READ, id);
    }
}

