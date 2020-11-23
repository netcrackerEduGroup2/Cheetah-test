package com.ncedu.cheetahtest.controller.action;

import com.ncedu.cheetahtest.entity.action.*;
import com.ncedu.cheetahtest.service.action.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${frontend.ulr}")

public class ActionsController {
    private final ActionService actionService;

    @Autowired
    public ActionsController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping("/actions")
    public List<Action> getAllActions() {
        return actionService.selectAllActions();
    }

    @PostMapping("/actions")
    public ResponseEntity<CreateActionResponse> createAction(@RequestParam(name = "id") int idLibrary, @RequestBody Action actionDTO) {
        actionService.createAction(idLibrary, actionDTO);
        return ResponseEntity.ok(new CreateActionResponse("Success"));

    }

    @GetMapping("actions/by-id/{id}")
    public Action getActionsByTitle(@PathVariable int id) {
        return actionService.getActionById(id);
    }


    @PutMapping("actions")
    public Action editAction(@RequestBody Action actionDTO) {
        return actionService.editAction(actionDTO);
    }

    @PutMapping("actions/change-status")
    public Action changeStatus(@RequestBody ChangeActionStatusDTO changeActionStatusDTO) {
        return actionService.changeStatus(changeActionStatusDTO.getStatusToChange(),
                changeActionStatusDTO.getId());
    }

    @DeleteMapping("/actions")
    public ResponseEntity<ActionStatusResponse> deleteAction(
            @RequestHeader("Authorisation") String token,
            @RequestBody DeleteActionDTO deleteActionDTO) {
        actionService.deleteAction(token,deleteActionDTO);
        return ResponseEntity.ok(new ActionStatusResponse("ActionDeletedSuccessfully"));
    }
}
