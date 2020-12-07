package com.ncedu.cheetahtest.controller.historyaction;


import com.ncedu.cheetahtest.entity.runaction.RunActionDto;
import com.ncedu.cheetahtest.service.historyaction.HistoryActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/run-test-case")
@CrossOrigin(origins = "${frontend.ulr}")
public class RunAction {

    private HistoryActionService<RunActionDto> historyActionService;

    @GetMapping("/action")
    public List<RunActionDto> getActionById(@RequestParam("id") int id){
        return historyActionService.getActionByIdTestCaseHistory(id);
    }
}
