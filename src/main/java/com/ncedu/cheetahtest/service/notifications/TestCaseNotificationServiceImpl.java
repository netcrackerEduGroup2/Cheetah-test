package com.ncedu.cheetahtest.service.notifications;

import com.ncedu.cheetahtest.dao.historytestcase.HistoryTestCaseDao;
import com.ncedu.cheetahtest.dao.notifications.NotificationsDao;
import com.ncedu.cheetahtest.dao.notifications.PaginatedTestCaseNotification;
import com.ncedu.cheetahtest.dao.project.ProjectDao;
import com.ncedu.cheetahtest.dao.user.UserDao;
import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCaseFull;
import com.ncedu.cheetahtest.entity.notification.NotificationStatus;
import com.ncedu.cheetahtest.entity.notification.ReadStatus;
import com.ncedu.cheetahtest.entity.notification.TestCaseNotification;
import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestCaseNotificationServiceImpl implements TestCaseNotificationService {
    private final NotificationsDao notificationsDao;
    private final HistoryTestCaseDao historyTestCaseDao;
    private final ProjectDao projectDao;
    private final UserDao userDao;
    private final WebSocketNotificationService wsNotificationService;


    @Override
    public void notifyAboutTestCaseCompletion(int historyTestCaseId) {
        TestCaseNotification testCaseNotification = new TestCaseNotification();
        HistoryTestCaseFull historyTestCase = historyTestCaseDao.getById(historyTestCaseId);
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
        testCaseNotification.setReadStatus(ReadStatus.UNREAD);

        List<Integer> userIds = userDao.getUsersIdByProjectId(projectId);

        for (int userId : userIds) {
            testCaseNotification.setUserId(userId);
            notificationsDao.createNotification(testCaseNotification);
        }
        sendNotificationsToUsers(userIds);


    }

    @Override
    public void notifyAboutTestCaseStatusChange(int historyTestCaseId, TestCaseResult testCaseResult) {
        HistoryTestCaseFull historyTestCase = historyTestCaseDao.getById(historyTestCaseId);
        int projectId = projectDao.findProjectByTestCaseId(historyTestCase.getIdTestCase()).getId();
        notificationsDao.changeStatusByTestCaseId(historyTestCase.getIdTestCase(), testCaseResult);
        List<Integer> userIds = userDao.getUsersIdByProjectId(projectId);
        sendNotificationsToUsers(userIds);

    }

    private void sendNotificationsToUsers(List<Integer> userIds) {
        for (int userId : userIds) {
            List<TestCaseNotification> notifications = this.getAllNotificationsByUserId(userId);
            wsNotificationService.sendNotificationToUser(userId, addTitleAndDescription(notifications));

        }
    }
    private List<TestCaseNotification> addTitleAndDescription(
            List<TestCaseNotification> testCaseNotifications){
        for (TestCaseNotification testCaseNotification: testCaseNotifications){
            if(testCaseNotification.getNotificationStatus().equals(NotificationStatus.COMPLETE)){
                testCaseNotification.setTitle("Test case "+testCaseNotification.getTestCaseId()+
                        " in project "+testCaseNotification.getProjectId()+" successfully ended");
                testCaseNotification.setDescription("Test case "+testCaseNotification.getTestCaseId()+
                        " in project "+testCaseNotification.getProjectId()+" successfully ended.\n " +
                        "For more detailed information please, follow the link below:");
            }
            if(testCaseNotification.getNotificationStatus().equals(NotificationStatus.FAILED)){
                testCaseNotification.setTitle("Test case "+testCaseNotification.getTestCaseId()+
                        " in project "+testCaseNotification.getProjectId()+" failed");
                testCaseNotification.setDescription("Test case "+testCaseNotification.getTestCaseId()+
                        " in project "+testCaseNotification.getProjectId()+" failed.\n " +
                        "For more detailed information please, follow the link below:");
            }
            else{
                testCaseNotification.setTitle("Test case "+testCaseNotification.getTestCaseId()+
                        " in project "+testCaseNotification.getProjectId()+" is in progress of executing");
                testCaseNotification.setDescription("Test case "+testCaseNotification.getTestCaseId()+
                        " in project "+testCaseNotification.getProjectId()+" is in progress of executing.\n " +
                        "For more detailed information please, follow the link below:");
            }
        }
        return testCaseNotifications;
    }

    @Override
    public void createNotification(TestCaseNotification testCaseNotification) {
        notificationsDao.createNotification(testCaseNotification);
    }

    @Override
    public TestCaseNotification editNotification(TestCaseNotification testCaseNotification, int id) {
        return notificationsDao.editNotification(testCaseNotification, id);
    }

    @Override
    public void deleteNotification(int id) {
        notificationsDao.deleteNotification(id);
    }

    @Override
    public List<TestCaseNotification> getAllNotificationsByUserId(int userId) {
        return notificationsDao.getAllNotificationsByUserId(userId);
    }

    @Override
    public PaginatedTestCaseNotification getNotificationsByUserIdPaginated(int userId, int size, int page) {
        int totalElements = notificationsDao.countNotificationsByUserId(userId);
        PaginatedTestCaseNotification paginatedTestCaseNotification = new PaginatedTestCaseNotification();
        paginatedTestCaseNotification.setTotalElements(totalElements);
        if (size * (page - 1) < totalElements) {
            paginatedTestCaseNotification.setTestCaseNotifications(
                    notificationsDao.getNotificationsByUserIdPaginated(userId, size, size * (page - 1)));
        }
        return paginatedTestCaseNotification;
    }

    @Override
    public TestCaseNotification editReadStatus(ReadStatus readStatus, int id) {
        TestCaseNotification testCaseNotification = notificationsDao.findById(id);
        testCaseNotification.setReadStatus(readStatus);
        return notificationsDao.editNotification(testCaseNotification,id);
    }
}
