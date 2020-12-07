package com.ncedu.cheetahtest.service.historytestcase;

import com.ncedu.cheetahtest.entity.history.HistoryTestCasePagination;

public interface HistoryTestCaseService {

    HistoryTestCasePagination getPage(int size, int page);
}
