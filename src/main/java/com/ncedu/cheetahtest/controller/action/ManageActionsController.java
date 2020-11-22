package com.ncedu.cheetahtest.controller.action;

import com.ncedu.cheetahtest.entity.action.*;
import com.ncedu.cheetahtest.service.action.ActionService;
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

    @GetMapping("/actions")
    public ResponseEntity<List<Action>> getAllActions() {
        return ResponseEntity.ok(this.actionService.selectAllActions());
    }

    @PostMapping("actions/create_action")
    public ResponseEntity<CreateActionResponse> createAction(@RequestParam(name = "id") int idLibrary,@RequestBody Action actionDTO) {
        actionService.createAction(idLibrary,actionDTO);
        return ResponseEntity.ok(new CreateActionResponse("Success"));

    }
    // TODO создать отдельный контроллер для library и добавить туда этот метод
    // он должен выдавать аналогично этому методу еще и лист компоундов и объединять их.
    // после этого их пейджирует и отдает на фронт
    @GetMapping("library/{idLibrary}")
    public ResponseEntity<List<Action>> getActionsByTitle(@PathVariable int idLibrary,@RequestParam(name = "title") String title) {
        return ResponseEntity.ok(actionService.getActionsByTitle(idLibrary,title));
    }

    @GetMapping("actions/by_id/{id}")
    public ResponseEntity<Action> getActionsByTitle(@PathVariable int id) {
        return ResponseEntity.ok(actionService.getActionById(id));
    }


    @PostMapping("actions/edit_action")
    public ResponseEntity<ActionStatusResponse> editAction(@RequestBody Action actionDTO) {
        actionService.editAction(actionDTO);
        return ResponseEntity.ok(new ActionStatusResponse("ActionChangedSuccessfully"));
    }
    @PostMapping("actions/change_status")
    public ResponseEntity<ActionStatusResponse> changeStatus(@RequestBody ChangeActionStatusDTO changeActionStatusDTO){
        actionService.changeStatus(changeActionStatusDTO.getStatusToChange(),
                changeActionStatusDTO.getId());
        return ResponseEntity.ok(new ActionStatusResponse("ActionStatusChangedSuccessfully"));
    }

    @PostMapping("actions/deleteAction")
    public ResponseEntity<ActionStatusResponse> changeStatus(@RequestBody DeleteActionDTO deleteActionDTO){
        actionService.deleteAction(deleteActionDTO);
        return ResponseEntity.ok(new ActionStatusResponse("ActionDeletedSuccessfully"));
    }
}
