package com.ncedu.cheetahtest.service.historytestcase;

import com.ncedu.cheetahtest.dao.historytestcase.HistoryTestCaseDao;
import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCase;
import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCaseDto;
import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCaseFull;
import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCasePagination;
import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;
import com.ncedu.cheetahtest.service.notifications.TestCaseNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryTestCaseServiceImpl implements HistoryTestCaseService {

    private final HistoryTestCaseDao historyTestCaseDao;
    private final TestCaseNotificationService testCaseNotificationService;

    @Override
    public HistoryTestCasePagination getPage(int id, int size, int page){
        List<HistoryTestCase> historyTestCases = historyTestCaseDao.getPage(id, size, page);
        int total = historyTestCaseDao.getCountTestCaseFailedCompleted();
        List<HistoryTestCaseDto> historyTestCaseDtos = new ArrayList<>();

        for (HistoryTestCase testCase : historyTestCases) {
            historyTestCaseDtos.add(
                    new HistoryTestCaseDto(
                            testCase.getId(),
                            testCase.getResult(),
                            testCase.getDataCompleted(),
                            testCase.getTitle()));
        }
        return new HistoryTestCasePagination(historyTestCaseDtos, total);
    }

    @Override
    public HistoryTestCaseFull create(String result, Date dateCompleted, int testCaseId) {
       int id =  historyTestCaseDao.addTestCase(result, dateCompleted, testCaseId);
       return historyTestCaseDao.getById(id);
    }

    @Override
    public HistoryTestCaseFull editHistoryTestCaseStatus(int idHistoryTestCase, TestCaseResult testCaseResult) {
        historyTestCaseDao.editTestCaseResultById(idHistoryTestCase,testCaseResult.toString());
        return historyTestCaseDao.getById(idHistoryTestCase);
    }
}
