package com.ncedu.cheetahtest.dao.historytestcase;

import com.ncedu.cheetahtest.entity.history.HistoryTestCase;

import java.util.List;

public interface HistoryTestCaseDao {

    void addTestCase(String result, String dateCompleted, int testCaseId);

    List<HistoryTestCase> getPage(int size, int page);
}
