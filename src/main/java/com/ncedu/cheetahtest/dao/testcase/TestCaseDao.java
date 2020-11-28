package com.ncedu.cheetahtest.dao.testcase;

import com.ncedu.cheetahtest.entity.testcase.TestCase;

import java.util.List;

public interface TestCaseDao {
    List<TestCase> getTestCases(int offset, int size);

    int getTotalElements();

    List<TestCase> getAllTestCases(int offset, int size);

    int getAllTotalElements();

    void save(TestCase testCase);

    TestCase findTestCaseById(int id);

    TestCase findTestCaseByTitleExceptCurrent(String title, int id);

    void deactivateTestCase(int id);

    List<TestCase> findTestCasesByTitlePaginated(int offset, int size, String title);

    int getSearchedTotalElements(String title);

    List<TestCase> findAllTestCasesByTitlePaginated(int offset, int size, String title);

    int getSearchedAllTotalElements(String title);

    int createTestCase(TestCase testCase);
}
