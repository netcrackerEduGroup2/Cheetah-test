package com.ncedu.cheetahtest.service.notifications;

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



    @Override
    @Async
    public void calculateAndSendProgress(int idTestCase,int totalActionResults, List<ActionResult> completed) {
        TestCaseProgressReport testCaseProgressReport = new TestCaseProgressReport();
        testCaseProgressReport.setTotalActionResults(totalActionResults);
        testCaseProgressReport.setCompleted(completed);
        testCaseProgressReport.setIdTestCase(idTestCase);

        wsNotificationService.sendProgressToAllUsers(testCaseProgressReport);
    }

}
