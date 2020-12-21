package com.ncedu.cheetahtest.service.historytestcase;

import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCaseFull;
import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCasePagination;
import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;

import java.util.Date;

public interface HistoryTestCaseService {

    HistoryTestCasePagination getPage(int size, int page);

    HistoryTestCasePagination getPage(int id, int size, int page);

    HistoryTestCaseFull create(String result, Date dateCompleted, int testCaseId);

    HistoryTestCaseFull editHistoryTestCaseStatus(int idHistoryTestCase, TestCaseResult testCaseResult);
}
