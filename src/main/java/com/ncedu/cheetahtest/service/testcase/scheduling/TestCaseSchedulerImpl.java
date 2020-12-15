package com.ncedu.cheetahtest.service.testcase.scheduling;

import com.ncedu.cheetahtest.dao.genericdao.AbstractDao;
import com.ncedu.cheetahtest.dao.testcase.TestCaseDao;
import com.ncedu.cheetahtest.entity.testcase.TestCase;
import com.ncedu.cheetahtest.exception.testcase.TestCaseScheduleAlreadyCreatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Service
public class TestCaseSchedulerImpl implements TestCaseScheduler {
    private final ThreadPoolTaskScheduler taskScheduler;
    private final ApplicationContext applicationContext;
    private final Map<Integer, ScheduledFuture<?>> testCaseScheduleMap;
    private final TestCaseDao testCaseDao;
    private final AbstractDao<TestCase> testCaseGenDao;

    @Autowired
    public TestCaseSchedulerImpl(ThreadPoolTaskScheduler taskScheduler,
                                 ApplicationContext applicationContext,
                                 TestCaseDao testCaseDao,
                                 AbstractDao<TestCase> testCaseGenDao) {

        this.taskScheduler = taskScheduler;
        this.applicationContext = applicationContext;
        this.testCaseDao = testCaseDao;
        this.testCaseGenDao = testCaseGenDao;

        testCaseScheduleMap = new HashMap<>();
    }

    @Override
    public void scheduleTestCases() {
        List<TestCase> testCases = testCaseDao.getActiveTestCasesWithExecutionDate();

        for (TestCase theTestCase : testCases) {
            scheduleAndPutInMapTestCase(theTestCase.getId(), theTestCase);
        }
    }

    @Override
    public void createTestCaseSchedule(int testCaseId) {
        if (testCaseScheduleMap.get(testCaseId) != null) {
            throw new TestCaseScheduleAlreadyCreatedException();
        }

        TestCase theTestCase = testCaseGenDao.findById(testCaseId);
        scheduleAndPutInMapTestCase(testCaseId, theTestCase);
    }

    @Override
    public void updateTestCaseSchedule(int testCaseId) {
        TestCase theTestCase = testCaseGenDao.findById(testCaseId);

        testCaseScheduleMap.get(testCaseId).cancel(false);

        scheduleAndPutInMapTestCase(testCaseId, theTestCase);
    }

    @Override
    public void deleteTestCaseSchedule(int testCaseId) {
        testCaseScheduleMap.get(testCaseId).cancel(false);
        testCaseScheduleMap.remove(testCaseId);
    }

    private void scheduleAndPutInMapTestCase(int testCaseId, TestCase theTestCase) {
        TestCaseRunnableTask task = applicationContext.getBean(TestCaseRunnableTask.class);
        task.setTestCase(theTestCase);

        ScheduledFuture<?> schedule = taskScheduler.schedule(
                task,
                new CronTrigger(theTestCase.getExecutionCronDate())
        );

        testCaseScheduleMap.put(testCaseId, schedule);
    }

    @Override
    public Map<Integer, ScheduledFuture<?>> getTestCaseScheduleMap() {
        return testCaseScheduleMap;
    }

}
