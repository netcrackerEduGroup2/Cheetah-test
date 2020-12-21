package com.ncedu.cheetahtest.dao.hiatoryaction;

import com.ncedu.cheetahtest.entity.actionresult.ActionResultForInfoDto;
import com.ncedu.cheetahtest.entity.runaction.RunAction;

import java.util.List;

public interface RunHistoryActionDao {
    List<RunAction> getRunActionByTestHistoryId(int testCaseHistoryId);
    List<Integer> getAllTestAcseHistoryId();
    List<ActionResultForInfoDto> getLastRunDetailsByHTCId(int idTestCase);
    List<ActionResultForInfoDto> getLastRunDetailsByHTCIdPaginated(int idTestCase, int limit, int offset);
    int countLastRunDetailsByHTCIdPaginated(int idTestCase);
}
