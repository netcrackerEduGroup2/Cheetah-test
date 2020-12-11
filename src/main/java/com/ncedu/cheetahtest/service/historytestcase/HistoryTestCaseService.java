package com.ncedu.cheetahtest.service.historytestcase;

import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCaseFull;
import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCasePagination;

import java.util.Date;

public interface HistoryTestCaseService {

    HistoryTestCasePagination getPage(int size, int page);

    HistoryTestCaseFull create(String result, Date dateCompleted, int testCaseId);
}
