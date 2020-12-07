package com.ncedu.cheetahtest.controller.history;

import com.ncedu.cheetahtest.entity.history.HistoryTestCasePagination;
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
        System.out.println(size);
        System.out.println(page);
        return historyTestCaseService.getPage(size, page);
    }

}
