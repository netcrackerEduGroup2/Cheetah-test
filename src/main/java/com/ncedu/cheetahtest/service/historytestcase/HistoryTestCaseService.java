package com.ncedu.cheetahtest.service.historytestcase;

import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCasePagination;

public interface HistoryTestCaseService {

    HistoryTestCasePagination getPage(int size, int page);
}
