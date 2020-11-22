package com.ncedu.cheetahtest.controller.library;

import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.compound.Compound;
import com.ncedu.cheetahtest.entity.library.CreateLibraryResponse;
import com.ncedu.cheetahtest.entity.library.Library;
import com.ncedu.cheetahtest.service.action.ActionService;
import com.ncedu.cheetahtest.service.compound.CompoundService;
import com.ncedu.cheetahtest.service.library.LibraryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")

public class ManageLibraryController {
    private final LibraryService libraryService;
    private final ActionService actionService;
    private final CompoundService compoundService;

    @Autowired
    public ManageLibraryController(LibraryService libraryService, ActionService actionService, CompoundService compoundService) {
        this.libraryService = libraryService;
        this.actionService = actionService;
        this.compoundService = compoundService;
    }




    @GetMapping("/libraries")
    public ResponseEntity<List<Library>> getAllLibraries(){
        return ResponseEntity.ok(libraryService.getAllLibraries());
    }

    @GetMapping ("libraries/{title}")
    public ResponseEntity<List<Library>> getLibraryById(@PathVariable String title){
        return ResponseEntity.ok(libraryService.getLibrariesByName(title));

    }

    @PostMapping("/create_library") //CreateLibraryResponse
    public ResponseEntity<CreateLibraryResponse> createLibrary(@RequestBody Library libraryDTO) {
        libraryService.createLibrary(libraryDTO);
        return ResponseEntity.ok(new CreateLibraryResponse("Success"));
    }
    @GetMapping("library/{idLibrary}")
    public ResponseEntity<List<Action>> getActComByTitle(@PathVariable int idLibrary, @RequestParam(name = "title") String title,
                                                         @Pe){
        List<Action> actions = actionService.getActionsByTitle(idLibrary,title);
        List<Compound> compounds = compoundService.getCompoundByTitle(idLibrary,title);
        List combined = actions;
        combined.addAll(compounds);

    }

}
