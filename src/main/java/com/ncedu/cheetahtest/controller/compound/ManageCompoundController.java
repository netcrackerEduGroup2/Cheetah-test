package com.ncedu.cheetahtest.controller.compound;

import com.ncedu.cheetahtest.entity.compound.*;
import com.ncedu.cheetahtest.service.compound.CompoundService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")

public class ManageCompoundController {
    private final CompoundService compoundService;

    @Autowired
    public ManageCompoundController(CompoundService compoundService) {
        this.compoundService = compoundService;
    }

    @GetMapping("/compounds")
    public ResponseEntity<List<Compound>> getAllActions() {
        return ResponseEntity.ok(this.compoundService.selectAllCompound());
    }

    @PostMapping("compounds/create_compound")
    public  ResponseEntity<CreateCompoundResponse> createCompound(@RequestParam(name = "id") int idLibrary,
                                                                  @RequestBody Compound compoundDTO) {
        compoundService.createCompound(idLibrary, compoundDTO);
        return ResponseEntity.ok(new CreateCompoundResponse("Success"));

    }

    @GetMapping("compounds/{libraryId}")
    public ResponseEntity<List<Compound>> getCompoundsByTitle(@PathVariable int libraryId, @RequestParam("title") String title) {
        return ResponseEntity.ok(compoundService.getCompoundByTitle(libraryId,title));
    }

    @PostMapping("compounds/edit_compound")
    public ResponseEntity<CompoundStatusResponse> editAction(@RequestBody Compound compoundDTO) {
        compoundService.editCompound(compoundDTO);
        return ResponseEntity.ok(new CompoundStatusResponse("CompoundChangedSuccessfully"));
    }

    @PostMapping("compounds/change_status")
    public ResponseEntity<CompoundStatusResponse> changeStatus(@RequestBody ChangeCompoundStatusDTO changeCompoundStatusDTO){
        compoundService.changeStatus(changeCompoundStatusDTO.getStatusToChange(),
                changeCompoundStatusDTO.getId());
        return ResponseEntity.ok(new CompoundStatusResponse("CompoundStatusChangedSuccessfully"));
    }

    @PostMapping("compounds/deleteCompound")
    public ResponseEntity<CompoundStatusResponse> deleteCompound(@RequestBody DeleteCompoundDTO deleteCompoundDTO){
        compoundService.deleteCompound(deleteCompoundDTO);
        return ResponseEntity.ok(new CompoundStatusResponse("CompoundDeletedSuccessfully"));
    }
}
