package com.ncedu.cheetahtest.controller.action;

import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.action.ActionDescriptionDTO;
import com.ncedu.cheetahtest.entity.action.PaginationAction;
import com.ncedu.cheetahtest.service.action.ActionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library/actions")
@CrossOrigin(origins = "${frontend.ulr}")
public class ActionController {
    private ActionService actionService;

    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }
    @GetMapping
    public PaginationAction getActionsByTitleLike(@RequestParam("title") String title,
                                                  @RequestParam("size") int size,
                                                  @RequestParam("page") int page){
        return actionService.getActionsByTitle(title,size,page);
    }

    @PutMapping("/{idAction}")
    public Action editActionDescription(@RequestBody ActionDescriptionDTO actionDescriptionDTO,
                                        @PathVariable int idAction){
        return actionService.editActionDescription(actionDescriptionDTO.getDescription(),idAction);
    }

}
