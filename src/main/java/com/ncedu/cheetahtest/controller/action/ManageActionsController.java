package com.ncedu.cheetahtest.controller.action;

import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.action.CreateActionResponse;
import com.ncedu.cheetahtest.service.action.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")

public class ManageActionsController {
    private final ActionService actionService;

    @Autowired
    public ManageActionsController(ActionService actionService) {
        this.actionService = actionService;
    }

    @PostMapping("/create_action")
    public ResponseEntity<CreateActionResponse> createAction(@RequestHeader("Authentication") String accessToken,
                                                             @RequestBody Action actionDTO) {
       actionService.createAction(accessToken,actionDTO);
       return ResponseEntity.ok(new CreateActionResponse("Success"));

    }

}
