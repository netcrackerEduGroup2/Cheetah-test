package com.ncedu.cheetahtest.dao.hiatoryaction;

import com.ncedu.cheetahtest.entity.runaction.RunAction;

import java.util.List;

public interface RunHistoryActionDao {
    List<RunAction> getRunActionByTestHistoryId(int testCaseHistoryId);
}
