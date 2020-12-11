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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TestCaseNotificationServiceImpl implements TestCaseNotificationService {
    private final NotificationsDao notificationsDao;
    private final HistoryTestCaseDao historyTestCaseDao;
    private final ProjectDao projectDao;
    private final UserDao userDao;

    @Autowired
    public TestCaseNotificationServiceImpl(NotificationsDao notificationsDao, HistoryTestCaseDao historyTestCaseDao,
                                           ProjectDao projectDao, UserDao userDao) {
        this.notificationsDao = notificationsDao;
        this.historyTestCaseDao = historyTestCaseDao;
        this.projectDao = projectDao;
        this.userDao = userDao;
    }


    @Override
    public void notifyAboutTestCaseCompletion(int historyTestCaseId) {
        TestCaseNotification testCaseNotification = new TestCaseNotification();
        HistoryTestCaseFull historyTestCase = historyTestCaseDao.getById(historyTestCaseId);
        if(historyTestCase.getResult() == TestCaseResult.COMPLETE){
            testCaseNotification.setNotificationStatus(NotificationStatus.COMPLETE) ;
        }else if(historyTestCase.getResult() == TestCaseResult.FAILED) {
            testCaseNotification.setNotificationStatus(NotificationStatus.FAILED);
        }else{
            testCaseNotification.setNotificationStatus(NotificationStatus.INPROCESS);
        }
        int projectId =projectDao.findProjectByTestCaseId(historyTestCase.getIdTestCase()).getId();
        testCaseNotification.setProjectId(projectId);
        testCaseNotification.setTestCaseId(historyTestCase.getIdTestCase());
        testCaseNotification.setReadStatus(ReadStatus.UNREAD);

        List<Integer> userIds = userDao.getUsersIdByProjectId(projectId);

        for(int userId: userIds){
            testCaseNotification.setUserId(userId);
            notificationsDao.createNotification(testCaseNotification);
        }


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


}
