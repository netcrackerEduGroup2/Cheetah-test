package com.ncedu.cheetahtest.service.historyaction;

import com.ncedu.cheetahtest.entity.historyacrion.HistoryAction;

import java.util.List;

public interface HistoryActionService {

    List<HistoryAction> getActionByIdTestCaseHistory(int idTestCaseHistory);
}
