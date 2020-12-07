package com.ncedu.cheetahtest.service.historyaction;

import com.ncedu.cheetahtest.dao.hiatoryaction.HistoryActionDao;
import com.ncedu.cheetahtest.entity.historyacrion.HistoryAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryActionServiceImpl implements HistoryActionService{

    private final HistoryActionDao historyActionDao;


    @Override
    public List<HistoryAction> getActionByIdTestCaseHistory(int idTestCaseHistory) {
        return historyActionDao.getHistoryActionByTestHistoryId(idTestCaseHistory);
    }
}
