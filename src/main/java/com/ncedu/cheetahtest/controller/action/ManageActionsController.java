package com.ncedu.cheetahtest.controller.action;

import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.action.CreateActionResponse;
import com.ncedu.cheetahtest.entity.library.Library;
import com.ncedu.cheetahtest.service.action.ActionService;
import com.ncedu.cheetahtest.service.library.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<CreateActionResponse> createAction(@RequestBody Action actionDTO) {
       actionService.createAction(actionDTO);
       return ResponseEntity.ok(new CreateActionResponse("Success"));

    }
}
