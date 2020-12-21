package com.ncedu.cheetahtest.controller.historyaction;


import com.ncedu.cheetahtest.entity.actionresult.ActionResultForInfoDto;
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

    private final RunActionService runActionService;

    @Autowired
    public RunAction (RunActionService runActionService) { this.runActionService = runActionService;}

    @GetMapping("/action")
    public List<ActionResultForInfoDto> getActionById(@RequestParam("id") int id){
        return runActionService.getActionsHistoryOfHTC(id);
    }

    @GetMapping("/id-test")
    public List<Integer> getAllIdTestCaseHistory() {
        return runActionService.getAllIdTestCase();
    }

}
