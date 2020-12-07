package com.ncedu.cheetahtest.service.historytestcase;

import com.ncedu.cheetahtest.dao.historytestcase.HistoryTestCaseDao;
import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCase;
import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCaseDto;
import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCasePagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryTestCaseServiceImpl implements HistoryTestCaseService {

    private final HistoryTestCaseDao historyTestCaseDao;

    @Override
    public HistoryTestCasePagination getPage(int size, int page){
        List<HistoryTestCase> historyTestCases = historyTestCaseDao.getPage(size, page);
        int total = 5;
        List<HistoryTestCaseDto> historyTestCaseDtos = new ArrayList<>();

        for (HistoryTestCase testCase : historyTestCases) {
            historyTestCaseDtos.add(
                    new HistoryTestCaseDto(
                            testCase.getId(),
                            testCase.getResult(),
                            testCase.getDataCompleted(),
                            testCase.getIdTestCase()));
        }
        return new HistoryTestCasePagination(historyTestCaseDtos, total);
    }
}
