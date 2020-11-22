package com.ncedu.cheetahtest.controller.library;

import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.action.PaginationResponceBody;
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


    @GetMapping ("/libraries")
    public ResponseEntity<PaginationResponceBody> getLibraryByName(@RequestParam(name = "title") String title,
    @RequestParam(name = "size") int size,
    @RequestParam(name = "page") int page){
        List<Library> libraries =libraryService.getLibrariesByName(title);
        int i=size*(page-1);
        List<Library> res;
        if(size*page>=libraries.size()){
            res= new ArrayList<>(libraries);
        }
        else {
            res = libraries.subList(size*(page-1),size*page);
        }

        PaginationResponceBody paginationResponceBody = new PaginationResponceBody();
        paginationResponceBody.setList(res);
        paginationResponceBody.setTotalElements(libraries.size());
        return ResponseEntity.ok(paginationResponceBody);

    }

    @PostMapping("/create_library") //CreateLibraryResponse
    public ResponseEntity<CreateLibraryResponse> createLibrary(@RequestBody Library libraryDTO) {
        libraryService.createLibrary(libraryDTO);
        return ResponseEntity.ok(new CreateLibraryResponse("Success"));
    }

    @GetMapping("library/{idLibrary}")
    public ResponseEntity<PaginationResponceBody> getActComByTitle(@PathVariable int idLibrary,
                                                         @RequestParam(name = "title") String title,
                                                         @RequestParam(name = "size") int size,
                                                         @RequestParam(name = "page") int page) {
        List<Action> actions = actionService.getActiveActionsByTitle(idLibrary, title);
        List<Compound> compounds = compoundService.getActiveCompoundByTitle(idLibrary, title);
        List combined = new ArrayList(actions);
        combined.addAll(compounds);
        List res;
        if(size*page>=combined.size()){
            res = new ArrayList(combined);
        }
        else{
            res = combined.subList(size*(page-1),size*page);
        }
        PaginationResponceBody paginationResponceBody = new PaginationResponceBody();
        paginationResponceBody.setList(res);
        paginationResponceBody.setTotalElements(res.size());
        return ResponseEntity.ok(paginationResponceBody);

    }
    @GetMapping("library/{idLibrary}/archive")
    public ResponseEntity<PaginationResponceBody> getInactiveActComByTitle(@PathVariable int idLibrary,
                                                                   @RequestParam(name = "title") String title,
                                                                   @RequestParam(name = "size") int size,
                                                                   @RequestParam(name = "page") int page) {
        List<Action> actions = actionService.getInactiveActionsByTitle(idLibrary, title);
        List<Compound> compounds = compoundService.getInactiveCompoundByTitle(idLibrary, title);
        List combined = new ArrayList(actions);
        combined.addAll(compounds);
        List res;
        if(size*page>=combined.size()){
            res = new ArrayList(combined);
        }
        else{
            res = combined.subList(size*(page-1),size*page);
        }
        PaginationResponceBody paginationResponceBody = new PaginationResponceBody();
        paginationResponceBody.setList(res);
        paginationResponceBody.setTotalElements(res.size());
        return ResponseEntity.ok(paginationResponceBody);

    }


}
