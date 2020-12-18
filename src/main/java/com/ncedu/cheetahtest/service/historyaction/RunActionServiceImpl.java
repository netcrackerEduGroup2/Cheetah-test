package com.ncedu.cheetahtest.service.historyaction;

import com.ncedu.cheetahtest.dao.hiatoryaction.RunHistoryActionDao;
import com.ncedu.cheetahtest.entity.actionresult.ActionResultForInfoDto;
import com.ncedu.cheetahtest.entity.pagination.PaginationContainer;
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

    @Override
    public List<ActionResultForInfoDto> getActionsHistoryOfHTC(int idHTC) {
        return runHistoryActionDao.getLastRunDetailsByHTCId(idHTC);
    }

    @Override
    public PaginationContainer<ActionResultForInfoDto> getActionsHistoryOfHTCPaginated(int idHTC, int size, int page) {
        PaginationContainer<ActionResultForInfoDto> paginationContainer = new PaginationContainer<>();
        int totalElements = runHistoryActionDao.countLastRunDetailsByHTCIdPaginated(idHTC);
        paginationContainer.setTotalElements(totalElements);
        if (size * (page - 1) < totalElements) {
            paginationContainer.setElements(runHistoryActionDao.getLastRunDetailsByHTCIdPaginated(idHTC, size, size * (page - 1)));
        }
        return paginationContainer;
    }
}
