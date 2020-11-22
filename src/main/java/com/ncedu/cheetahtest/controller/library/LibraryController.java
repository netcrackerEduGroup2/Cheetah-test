package com.ncedu.cheetahtest.controller.library;

import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.action.PaginationResponseBody;
import com.ncedu.cheetahtest.entity.compound.Compound;
import com.ncedu.cheetahtest.entity.library.CreateLibraryResponse;
import com.ncedu.cheetahtest.entity.library.Library;
import com.ncedu.cheetahtest.entity.library.LibraryPaginationResponseBody;
import com.ncedu.cheetahtest.entity.library.LibraryStatusResponce;
import com.ncedu.cheetahtest.service.action.ActionService;
import com.ncedu.cheetahtest.service.compound.CompoundService;
import com.ncedu.cheetahtest.service.library.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${frontend.ulr}")

public class LibraryController {
    private final LibraryService libraryService;
    private final ActionService actionService;
    private final CompoundService compoundService;

    @Autowired
    public LibraryController(LibraryService libraryService, ActionService actionService, CompoundService compoundService) {
        this.libraryService = libraryService;
        this.actionService = actionService;
        this.compoundService = compoundService;
    }


    @GetMapping("/libraries")
    public LibraryPaginationResponseBody getLibraryByName(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "page") int page) {
        List<Library> libraries = libraryService.getLibrariesByName(title);
        return new LibraryPaginationResponseBody(libraries, size, page);

    }

    @PostMapping("/create-library") //CreateLibraryResponse
    public ResponseEntity<CreateLibraryResponse> createLibrary(@RequestBody Library libraryDTO) {
        libraryService.createLibrary(libraryDTO);
        return ResponseEntity.ok(new CreateLibraryResponse("Success"));
    }

    @GetMapping("library/{idLibrary}")
    public PaginationResponseBody getActiveActComByTitle(@PathVariable int idLibrary,
                                                         @RequestParam(name = "title") String title,
                                                         @RequestParam(name = "size") int size,
                                                         @RequestParam(name = "page") int page) {
        List<Action> actions = actionService.getActiveActionsByTitle(idLibrary, title);
        List<Compound> compounds = compoundService.getActiveCompoundByTitle(idLibrary, title);
        return new PaginationResponseBody(actions, compounds, size, page);

    }

    @GetMapping("library/{idLibrary}/archive")
    public PaginationResponseBody getInactiveActComByTitle(@PathVariable int idLibrary,
                                                           @RequestParam(name = "title") String title,
                                                           @RequestParam(name = "size") int size,
                                                           @RequestParam(name = "page") int page) {
        List<Action> actions = actionService.getInactiveActionsByTitle(idLibrary, title);
        List<Compound> compounds = compoundService.getInactiveCompoundByTitle(idLibrary, title);
        return new PaginationResponseBody(actions, compounds, size, page);

    }

    @DeleteMapping("library/{idLibrary}/delete-library")
    public ResponseEntity<LibraryStatusResponce> deleteLibrary(@PathVariable int idLibrary,
                                                               @RequestHeader("Authentication") String token) {
        libraryService.deleteLibrary(token, idLibrary);
        return ResponseEntity.ok(new LibraryStatusResponce("LibraryDeletedSuccessfully"));
    }
    @PutMapping("/library/{idLibrary}/edit")
    public Library editLibrary(@RequestBody Library library, @PathVariable int idLibrary){
        return libraryService.editLibrary(library,idLibrary);
    }


}
