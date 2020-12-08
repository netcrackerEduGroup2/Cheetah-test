package com.ncedu.cheetahtest.controller.historyaction;


import com.ncedu.cheetahtest.entity.runaction.RunActionDto;
import com.ncedu.cheetahtest.service.historyaction.RunActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/run-test-case")
@CrossOrigin(origins = "${frontend.ulr}")
public class RunAction {

    private RunActionService runActionService;

    @Autowired
    public RunAction (RunActionService runActionService) { this.runActionService = runActionService;}

    @GetMapping("/action")
    public List<RunActionDto> getActionById(@RequestParam("id") int id){
        return runActionService.getActionByIdTestCaseHistory(id);
    }

    @GetMapping("/id-test")
    public List<Integer> getAllIdTestCaseHistory() {
        return runActionService.getAllIdTestCase();
    }
}
