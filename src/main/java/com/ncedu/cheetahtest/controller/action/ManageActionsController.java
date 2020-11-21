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
    private final LibraryService libraryService;

    @Autowired
    public ManageActionsController(ActionService actionService, LibraryService libraryService) {
        this.actionService = actionService;
        this.libraryService = libraryService;
    }

    @PostMapping("/create_action")
    public ResponseEntity<CreateActionResponse> createAction(@RequestBody Action actionDTO) {
       actionService.createAction(actionDTO);
       return ResponseEntity.ok(new CreateActionResponse("Success"));

    }

    @GetMapping("/libraries")
    public ResponseEntity<List<Library>> getAllLibraries(){
        return ResponseEntity.ok(libraryService.getAllLibraries());
    }

    @GetMapping ("libraries/{title}")
    public ResponseEntity<List<Library>> getLibraryById(@PathVariable String title){
        System.out.println("123"+title);
        return ResponseEntity.ok(libraryService.getLibrariesByName(title));

    }


}
