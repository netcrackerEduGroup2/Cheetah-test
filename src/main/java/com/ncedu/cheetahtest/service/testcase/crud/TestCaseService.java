package com.ncedu.cheetahtest.service.testcase.crud;

import com.ncedu.cheetahtest.entity.testcase.TestCase;
import com.ncedu.cheetahtest.entity.testcase.TestCasePaginated;
import com.ncedu.cheetahtest.entity.testcase.TestCaseScheduleDto;

public interface TestCaseService {

    TestCasePaginated getTestCases(int page, int size);

    TestCasePaginated getAllTestCases(int page, int size);

    void save(TestCase testCase);

    TestCase findTestCaseByProjectIdAndTestCaseId(int projectId, int id);

    void deactivateTestCase(int id);

    TestCasePaginated findTestCasesByTitlePaginated(int page, int size, String title);

    TestCasePaginated findAllTestCasesByTitlePaginated(int page, int size, String title);

    int createTestCase(TestCase testCase);

    TestCasePaginated getActiveTestCasesPaginatedByProjectId(int page, int size, int projectId);

    TestCasePaginated findTestCasesByTitlePaginatedAndByProjectId(int page, int size, String keyword, int projectId);

    void updateExecutionCronDateAndRepeatability(TestCaseScheduleDto testCaseScheduleDto);

    void deleteExecutionCronDateAndRepeatability(int testCaseId);
}
