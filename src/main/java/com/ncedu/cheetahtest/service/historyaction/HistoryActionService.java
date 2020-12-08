package com.ncedu.cheetahtest.service.historyaction;

import java.util.List;

public interface HistoryActionService<T> {

    List<T> getActionByIdTestCaseHistory(int idTestCaseHistory);
}
