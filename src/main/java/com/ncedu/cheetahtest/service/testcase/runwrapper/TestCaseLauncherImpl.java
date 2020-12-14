package com.ncedu.cheetahtest.service.testcase.runwrapper;

import com.ncedu.cheetahtest.dao.actscenario.ActScenarioDao;
import com.ncedu.cheetahtest.dao.compound.CompoundDao;
import com.ncedu.cheetahtest.dao.hiatoryaction.HistoryActionDao;
import com.ncedu.cheetahtest.dao.historytestcase.HistoryTestCaseDao;
import com.ncedu.cheetahtest.entity.actscenario.ActScenario;
import com.ncedu.cheetahtest.entity.selenium.ActionResult;
import com.ncedu.cheetahtest.entity.selenium.ActionResultStatus;
import com.ncedu.cheetahtest.entity.selenium.SeleniumAction;
import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;
import com.ncedu.cheetahtest.service.notifications.TestCaseNotificationService;
import com.ncedu.cheetahtest.service.notifications.TestCaseProgressService;
import com.ncedu.cheetahtest.service.selenium.TestCaseExecutor;
import com.ncedu.cheetahtest.service.selenium.TestCaseExecutorImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableAsync
public class TestCaseLauncherImpl implements TestCaseLauncher {

    private final ActScenarioDao actScenarioDao;
    private final CompoundDao compoundDao;
    private final ApplicationContext applicationContext;
    private final HistoryActionDao historyActionDao;
    private final HistoryTestCaseDao historyTestCaseDao;
    private final TestCaseProgressService testCaseProgressService;
    private final TestCaseNotificationService tcnService;

    @Override
    @Transactional
    @Async
    public void formActionForSelenium(int testCaseId) {
        List<ActScenario> actScenarios = actScenarioDao.getAllByTestCaseId(testCaseId);

        List<SeleniumAction> seleniumActions = mapActScenarioToSeleniumAction(actScenarios);
        processActions(seleniumActions, testCaseId);
    }

    public List<SeleniumAction> mapActScenarioToSeleniumAction(
            List<ActScenario> actScenarios) {

        List<SeleniumAction> seleniumActions = new ArrayList<>();

        actScenarios.forEach((actScenario -> {
            Integer compId = compoundDao.getCompoundIdByActionId(actScenario.getActionId());
            seleniumActions.add(new SeleniumAction(
                    actScenario.getActionId(),
                    compId,
                    actScenario.getActionType(),
                    actScenario.getParameterType(),
                    actScenario.getParameterValue()));
        }));

        return seleniumActions;
    }

    public List<ActionResult> processActions(List<SeleniumAction> actionList, int testCaseId) {

        TestCaseExecutor testCaseExecutor = applicationContext.getBean(TestCaseExecutorImpl.class);

        List<ActionResult> actionResults = new ArrayList<>();

        //this int added to perform progress of testcase notifications
        int totalActionResults = actionList.size();
        //----

        int testCaseHistoryId = historyTestCaseDao.addTestCase(
                TestCaseResult.CREATED.toString(),
                new Date(),
                testCaseId
        );
        tcnService.notifyAboutTestCaseExecution(testCaseHistoryId);


        for (int i = 0; i < actionList.size(); i++) {
            SeleniumAction theAction = actionList.get(i);

            ActionResult theActionResult = testCaseExecutor.executeAction(theAction);

            log.info(theActionResult.toString());

            actionResults.add(theActionResult);

            testCaseProgressService.calculateAndSendProgress(testCaseId,totalActionResults,actionResults);

            Integer compId = theActionResult.getAction().getCompoundId();

            if (compId != null) {
                historyActionDao.addAction(
                        theActionResult.getResultDescription(),
                        theActionResult.getScreenshotUrl(),
                        i + 1,
                        testCaseHistoryId,
                        compId,
                        theActionResult.getAction().getActionId(),
                        theActionResult.getAction().getElement(),
                        theActionResult.getAction().getArgument());
            } else {
                historyActionDao.addAction(
                        theActionResult.getResultDescription(),
                        theActionResult.getScreenshotUrl(),
                        i + 1,
                        testCaseHistoryId,
                        theActionResult.getAction().getActionId(),
                        theActionResult.getAction().getElement(),
                        theActionResult.getAction().getArgument());
            }

            if (theActionResult.getStatus().equals(ActionResultStatus.FAIL)) {
                historyTestCaseDao.editTestCaseResultById(testCaseHistoryId,"FAILED");
                tcnService.notifyAboutTestCaseStatusChange(testCaseHistoryId,TestCaseResult.FAILED);
                break;
            }
        }
        historyTestCaseDao.editTestCaseResultById(testCaseHistoryId,"COMPLETE");
        tcnService.notifyAboutTestCaseStatusChange(testCaseHistoryId,TestCaseResult.COMPLETE);
        testCaseExecutor.close();
        return actionResults;
    }
}
