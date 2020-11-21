package com.ncedu.cheetahtest.controller.library;

import com.ncedu.cheetahtest.entity.library.Library;
import com.ncedu.cheetahtest.service.library.LibraryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")

public class ManageLibraryController {
    private final LibraryService libraryService;

    @Autowired
    public ManageLibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    /*@PostMapping("/create_library")
    public ResponseEntity<CreateActionResponse> createLibrary(@RequestBody Library libraryDTO) {
        libraryService.
    }*/

    @GetMapping("/libraries")
    public ResponseEntity<List<Library>> getAllLibraries(){
        return ResponseEntity.ok(libraryService.getAllLibraries());
    }

    @GetMapping ("libraries/{title}")
    public ResponseEntity<List<Library>> getLibraryById(@PathVariable String title){
        return ResponseEntity.ok(libraryService.getLibrariesByName(title));

    }
}
