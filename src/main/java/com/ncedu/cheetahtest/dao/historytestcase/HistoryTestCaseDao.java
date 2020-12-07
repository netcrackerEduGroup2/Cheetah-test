package com.ncedu.cheetahtest.dao.historytestcase;

import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCase;

import java.util.List;

public interface HistoryTestCaseDao {

    void addTestCase(String result, String dateCompleted, int testCaseId);

    void editTestCaseResultById(int testCaseId, String result);

    List<HistoryTestCase> getPage(int size, int page);
}
