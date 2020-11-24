package com.ncedu.cheetahtest.controller.action;

import com.ncedu.cheetahtest.entity.action.*;
import com.ncedu.cheetahtest.service.action.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actions")
@CrossOrigin(origins = "${frontend.ulr}")

public class ActionsController {
    private final ActionService actionService;

    @Autowired
    public ActionsController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping("/")
    public List<Action> getAllActions() {
        return actionService.selectAllActions();
    }

    @PostMapping("/")
    public ResponseEntity<CreateActionResponse> createAction(@RequestParam(name = "id") int idLibrary, @RequestBody Action actionDTO) {
        actionService.createAction(idLibrary, actionDTO);
        return ResponseEntity.ok(new CreateActionResponse("Success"));

    }

    @GetMapping("by-id/{id}")
    public Action getActionsByTitle(@PathVariable int id) {
        return actionService.getActionById(id);
    }


    @PutMapping("/")
    public Action editAction(@RequestBody Action actionDTO) {
        return actionService.editAction(actionDTO);
    }

    @PutMapping("/change-status")
    public Action changeStatus(@RequestBody ChangeActionStatusDTO changeActionStatusDTO) {
        return actionService.changeStatus(changeActionStatusDTO.getStatusToChange(),
                changeActionStatusDTO.getId());
    }

    @DeleteMapping("/")
    public ResponseEntity<ActionStatusResponse> deleteAction(
            @RequestHeader("Authorisation") String token,
            @RequestBody DeleteActionDTO deleteActionDTO) {
        actionService.deleteAction(token,deleteActionDTO);
        return ResponseEntity.ok(new ActionStatusResponse("ActionDeletedSuccessfully"));
    }
}
