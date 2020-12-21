package com.ncedu.cheetahtest.service.notifications;

import com.ncedu.cheetahtest.dao.project.ProjectDao;
import com.ncedu.cheetahtest.dao.user.UserDao;
import com.ncedu.cheetahtest.entity.progress.TestCaseProgressReport;
import com.ncedu.cheetahtest.entity.selenium.ActionResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestCaseProgressServiceImpl implements TestCaseProgressService{
    private final WebSocketNotificationService wsNotificationService;

    private final ProjectDao projectDao;
    private final UserDao userDao;


    @Override
    @Async
    public void calculateAndSendProgress(int idTestCase,int totalActionResults, List<ActionResult> completed) {
        TestCaseProgressReport testCaseProgressReport = new TestCaseProgressReport();
        testCaseProgressReport.setTotalActionResults(totalActionResults);
        testCaseProgressReport.setCompleted(completed);
        testCaseProgressReport.setIdTestCase(idTestCase);

        int projectId = projectDao.findProjectByTestCaseId(idTestCase).getId();
        List<Integer> userIds = userDao.getUsersIdByProjectId(projectId);
        sendProgressToUsers(userIds,testCaseProgressReport);
    }


    private void sendProgressToUsers(List<Integer> userIds, TestCaseProgressReport testCaseProgressReport) {
        for (int userId : userIds) {
            wsNotificationService.sendProgressToUser(userId,testCaseProgressReport);

        }
    }
}
