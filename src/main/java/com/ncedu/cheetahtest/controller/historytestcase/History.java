package com.ncedu.cheetahtest.controller.historytestcase;

import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCaseFull;
import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCasePagination;
import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;
import com.ncedu.cheetahtest.service.historytestcase.HistoryTestCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/history")
@CrossOrigin(origins = "${frontend.ulr}")
public class History {

    private final HistoryTestCaseService historyTestCaseService;

    @Autowired
    public History(HistoryTestCaseService historyTestCaseService) { this.historyTestCaseService = historyTestCaseService;}

    @GetMapping("/test-case")
    public HistoryTestCasePagination getHistoryTestCase(@RequestParam("size") int size,
                                                        @RequestParam("page") int page){
        return historyTestCaseService.getPage(size, page);
    }
    @PostMapping
    public HistoryTestCaseFull createHistoryTestCase(@RequestBody HistoryTestCaseFull historyTestCaseFull){
        return historyTestCaseService.create(historyTestCaseFull.getResult().toString(),
                historyTestCaseFull.getDataCompleted(),historyTestCaseFull.getIdTestCase());
    }

    @PutMapping("/{idHistoryTestCase}")
    public HistoryTestCaseFull editHistoryTestCaseStatus(@PathVariable("idHistoryTestCase") int idHistoryTestCase,
                                                     @RequestBody TestCaseResult testCaseResult){
        return historyTestCaseService.editHistoryTestCaseStatus(idHistoryTestCase,testCaseResult);
    }

}
