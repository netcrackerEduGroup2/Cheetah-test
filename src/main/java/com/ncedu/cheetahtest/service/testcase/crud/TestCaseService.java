package com.ncedu.cheetahtest.service.testcase.crud;

import com.ncedu.cheetahtest.entity.pagination.PaginationContainer;
import com.ncedu.cheetahtest.entity.testcase.TestCase;
import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;
import com.ncedu.cheetahtest.entity.testcase.TestCaseScheduleDto;

import java.util.List;

public interface TestCaseService {

    PaginationContainer<TestCase> getTestCases(int page, int size);

    PaginationContainer<TestCase> getAllTestCases(int page, int size);

    void save(TestCase testCase);

    TestCase findTestCaseByProjectIdAndTestCaseId(int projectId, int id);

    void deactivateTestCase(int id);

    PaginationContainer<TestCase> findTestCasesByTitlePaginated(int page, int size, String title);

    PaginationContainer<TestCase> findAllTestCasesByTitlePaginated(int page, int size, String title);

    int createTestCase(TestCase testCase);

    PaginationContainer<TestCase> getActiveTestCasesPaginatedByProjectId(int page, int size, int projectId);

    PaginationContainer<TestCase> findTestCasesByTitlePaginatedAndByProjectId(int page, int size, String keyword, int projectId);

    void updateExecutionCronDateAndRepeatability(TestCaseScheduleDto testCaseScheduleDto);

    void deleteExecutionCronDateAndRepeatability(int testCaseId);

    List<TestCase> getActiveTestCasesWithExecutionDate();

    List<TestCase> getAllActiveTestCasesByTitle(String title);

    PaginationContainer<TestCase> findTestCasesByTitlePaginatedAndByProjectIdAndResult
            (int page, int size, String keyword, String result, int projectId);
}
