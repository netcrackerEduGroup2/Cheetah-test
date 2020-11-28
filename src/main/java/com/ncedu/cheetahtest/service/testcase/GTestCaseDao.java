package com.ncedu.cheetahtest.service.testcase;

import com.ncedu.cheetahtest.entity.testcase.TestCase;


public interface GTestCaseDao {

    void save(TestCase testCase);

    TestCase findTestCaseByTitleExceptCurrent(String title, int id);

    int createTestCase(TestCase testCase);
}
