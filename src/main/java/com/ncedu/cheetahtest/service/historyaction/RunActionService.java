package com.ncedu.cheetahtest.service.historyaction;

import com.ncedu.cheetahtest.entity.runaction.RunActionDto;

import java.util.List;

public interface RunActionService {

    List<RunActionDto> getActionByIdTestCaseHistory(int idTestCaseHistory);

    List<Integer> getAllIdTestCase();
}
