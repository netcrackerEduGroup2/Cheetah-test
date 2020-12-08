package com.ncedu.cheetahtest.service.historyaction;

import com.ncedu.cheetahtest.dao.hiatoryaction.RunHistoryActionDao;
import com.ncedu.cheetahtest.entity.runaction.RunAction;
import com.ncedu.cheetahtest.entity.runaction.RunActionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RunActionServiceImpl implements RunActionService  {

    private final RunHistoryActionDao runHistoryActionDao;

    @Override
    public List<RunActionDto> getActionByIdTestCaseHistory(int idTestCaseHistory) {
        List<RunAction> runActions = runHistoryActionDao.getRunActionByTestHistoryId(idTestCaseHistory);
        List<RunActionDto> runActionDtos = new ArrayList<>();

        for (RunAction action : runActions) {
            runActionDtos.add(
                    new RunActionDto(
                            action.getId(),
                            action.getCompoundId(),
                            action.getResult(),
                            action.getScreenshotURL(),
                            action.getActionType(),
                            action.getElement(),
                            action.getArgument()));
        }
        return runActionDtos;
    }

    @Override
    public List<Integer> getAllIdTestCase() {
        return runHistoryActionDao.getAllTestAcseHistoryId();
    }
}
