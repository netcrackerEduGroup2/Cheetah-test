package com.ncedu.cheetahtest.dao.testcase;

import com.ncedu.cheetahtest.entity.testcase.TestCase;
import com.ncedu.cheetahtest.entity.testcase.TestCaseScheduleDto;

import java.util.List;

public interface TestCaseDao {

    void save(TestCase testCase);

    TestCase findTestCaseByProjectIdAndTestCaseId(int projectId, int id);

    TestCase findTestCaseByTitleExceptCurrent(String title, int id);

    int createTestCase(TestCase testCase);

    void deactivate(int id);

    List<TestCase> getActiveTestCasesPaginatedByProjectId(int page, int size, int projectId);

    int getAmountActiveElementsByProjectId(int projectId);

    List<TestCase> findTestCasesByTitlePaginatedAndByProjectId(int page, int size, String keyword, int projectId);

    int getAmountByTitlePaginatedAndByProjectId(String keyword, int projectId);

    boolean getTestCaseRepeatable(int id);

    String getExecutionDateById(int id);

    List<TestCase> getAllActiveTestCasesByTitle(String title);

    List<TestCase> getActiveTestCasesWithExecutionDate();

    void setExecutionDateToNull(int id);

    void deleteExecutionCronDateAndRepeatability(int testCaseId);

    void updateExecutionCronDateAndRepeatability(TestCaseScheduleDto testCaseScheduleDto);

    void setResultToSuccess(int testCaseId);

    void setResultToFail(int testCaseId);
}
