package com.ncedu.cheetahtest.service.testcase.scheduling;

import com.ncedu.cheetahtest.entity.testcase.TestCaseScheduleDto;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public interface TestCaseScheduler {
    void scheduleTestCases();

    Map<Integer, ScheduledFuture<?>> getTestCaseScheduleMap();

    void createTestCaseSchedule(TestCaseScheduleDto testCaseScheduleDto);

    void updateTestCaseSchedule(TestCaseScheduleDto testCaseScheduleDto);

    void deleteTestCaseSchedule(int testCaseId);
}
