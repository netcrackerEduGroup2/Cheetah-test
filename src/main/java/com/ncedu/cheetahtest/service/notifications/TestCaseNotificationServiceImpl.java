package com.ncedu.cheetahtest.service.notifications;

import com.ncedu.cheetahtest.dao.notifications.NotificationsDao;
import com.ncedu.cheetahtest.dao.notifications.PaginatedTestCaseNotification;
import com.ncedu.cheetahtest.entity.notification.ReadStatus;
import com.ncedu.cheetahtest.entity.notification.TestCaseNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestCaseNotificationServiceImpl implements TestCaseNotificationService {
    private final NotificationsDao notificationsDao;

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
