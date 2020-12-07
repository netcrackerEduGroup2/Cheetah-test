package com.ncedu.cheetahtest.dao.historytestcase;

import com.ncedu.cheetahtest.entity.history.HistoryTestCase;

import java.util.Date;
import java.util.List;

public interface HistoryTestCaseDao {

    int addTestCase(String result, Date dateCompleted, int testCaseId);

    void editTestCaseResultById(int testCaseId, String result);

    List<HistoryTestCase> getPage(int size, int page);
}
