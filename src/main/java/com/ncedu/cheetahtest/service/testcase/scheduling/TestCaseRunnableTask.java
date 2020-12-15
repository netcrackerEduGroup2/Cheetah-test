package com.ncedu.cheetahtest.service.testcase.scheduling;

import com.ncedu.cheetahtest.dao.testcase.TestCaseDao;
import com.ncedu.cheetahtest.entity.testcase.TestCase;
import com.ncedu.cheetahtest.service.testcase.runwrapper.TestCaseLauncher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TestCaseRunnableTask implements Runnable {
    private TestCase testCase;
    private final TestCaseLauncher testCaseLauncher;
    private final TestCaseScheduler testCaseScheduler;
    private final TestCaseDao testCaseDao;

    @Override
    public void run() {
        testCaseLauncher.formActionForSelenium(testCase.getId());
        if (!testCase.isRepeatable()) {
            testCaseScheduler.getTestCaseScheduleMap().get(testCase.getId()).cancel(false);
            testCaseScheduler.getTestCaseScheduleMap().remove(testCase.getId());
            testCaseDao.setExecutionDateToNull(testCase.getId());
        }

        log.info("Test case with id " +  testCase.getId() +  " is running. Time: " + new Date());
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }
}
