package com.ncedu.cheetahtest.dao.hiatoryaction;

import com.ncedu.cheetahtest.entity.historyacrion.HistoryAction;

import java.util.List;

public interface HistoryActionDao {

    void addAction(String result, String screenshotURL, int generalOrder,
                   int idHistoryTestCase, int compoundId);

    List<HistoryAction> getHistoryActionByTestHistoryId(int testCaseHistoryId);
}
