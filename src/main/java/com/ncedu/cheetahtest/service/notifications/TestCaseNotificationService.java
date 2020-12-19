package com.ncedu.cheetahtest.service.notifications;

import com.ncedu.cheetahtest.dao.notifications.PaginatedTestCaseNotification;
import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCaseFull;
import com.ncedu.cheetahtest.entity.notification.ReadStatus;
import com.ncedu.cheetahtest.entity.notification.TestCaseNotification;
import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;
import com.ncedu.cheetahtest.entity.testcase.TestCaseStatus;

import java.util.List;

public interface TestCaseNotificationService {
    void createNotification(TestCaseNotification testCaseNotification);

    TestCaseNotification editNotification(TestCaseNotification testCaseNotification, int id);

    void deleteNotification(int id);

    List<TestCaseNotification> getAllNotificationsByUserId (int userId);

    PaginatedTestCaseNotification getNotificationsByUserIdPaginated(int userId, int size, int page);

    //methods below are used by performing tests methods to notify users
    void notifyAboutTestCaseExecution(HistoryTestCaseFull historyTestCaseFull);

    void notifyAboutTestCaseStatusChange(HistoryTestCaseFull historyTestCaseFull, TestCaseResult testCaseResult);

    TestCaseNotification editReadStatus(ReadStatus readStatus,int id);
}
