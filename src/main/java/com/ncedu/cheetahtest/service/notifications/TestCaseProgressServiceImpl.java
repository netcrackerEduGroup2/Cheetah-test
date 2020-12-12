package com.ncedu.cheetahtest.service.notifications;

import com.ncedu.cheetahtest.entity.progress.TestCaseProgressReport;
import com.ncedu.cheetahtest.entity.selenium.ActionResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class TestCaseProgressServiceImpl implements TestCaseProgressService{
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void calculateAndSendProgress(int idTestCase,int totalActionResults, List<ActionResult> completed) {
        TestCaseProgressReport testCaseProgressReport = new TestCaseProgressReport();
        testCaseProgressReport.setTotalActionResults(totalActionResults);
        testCaseProgressReport.setCompleted(completed);
        //todo Delete that
        log.info("\n Progress Report:\n");
        log.info(testCaseProgressReport.toString());
        log.info("\n");


        simpMessagingTemplate.convertAndSend("/topic/progress"+idTestCase, testCaseProgressReport);
    }
}
