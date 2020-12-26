package com.ncedu.cheetahtest.dao.historytestcase;

import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCase;
import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCaseFull;

import java.util.Date;
import java.util.List;

public interface HistoryTestCaseDao {

    int addTestCase(String result, Date dateCompleted, int testCaseId);

    HistoryTestCaseFull getById(int id);

    void editTestCaseResultById(int testCaseId, String result);

    Integer getCountTestCaseFailedCompleted(int idTestCase);

    List<HistoryTestCase> getPage(int id, int size, int page);

}
