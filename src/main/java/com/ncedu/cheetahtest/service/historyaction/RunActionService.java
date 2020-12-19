package com.ncedu.cheetahtest.service.historyaction;

import com.ncedu.cheetahtest.entity.actionresult.ActionResultForInfoDto;
import com.ncedu.cheetahtest.entity.pagination.PaginationContainer;
import com.ncedu.cheetahtest.entity.runaction.RunActionDto;

import java.util.List;

public interface RunActionService {

    List<RunActionDto> getActionByIdTestCaseHistory(int idTestCaseHistory);

    List<Integer> getAllIdTestCase();

    List<ActionResultForInfoDto> getActionsHistoryOfHTC(int idHTC);

    PaginationContainer<ActionResultForInfoDto> getActionsHistoryOfHTCPaginated(int idHTC, int size, int page);

}
