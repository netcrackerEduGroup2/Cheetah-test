package com.ncedu.cheetahtest.service.testcase.scheduling;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public interface TestCaseScheduler {
    void scheduleTestCases();

    Map<Integer, ScheduledFuture<?>> getTestCaseScheduleMap();

    void createTestCaseSchedule(int testCaseId);

    void updateTestCaseSchedule(int testCaseId);

    void deleteTestCaseSchedule(int testCaseId);
}
