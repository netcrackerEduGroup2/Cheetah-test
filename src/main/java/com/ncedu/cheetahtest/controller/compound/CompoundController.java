package com.ncedu.cheetahtest.controller.compound;

import com.ncedu.cheetahtest.entity.compound.*;
import com.ncedu.cheetahtest.service.compound.CompoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${frontend.ulr}")

public class CompoundController {
    private final CompoundService compoundService;

    @Autowired
    public CompoundController(CompoundService compoundService) {
        this.compoundService = compoundService;
    }

    @GetMapping("/compounds")
    public ResponseEntity<List<Compound>> getAllActions() {
        return ResponseEntity.ok(this.compoundService.selectAllCompound());
    }

    @PostMapping("compounds/create-compound")
    public ResponseEntity<CreateCompoundResponse> createCompound(@RequestParam(name = "id") int idLibrary,
                                                                 @RequestBody Compound compoundDTO) {
        compoundService.createCompound(idLibrary, compoundDTO);
        return ResponseEntity.ok(new CreateCompoundResponse("Success"));

    }

    @GetMapping("compounds/{libraryId}")
    public List<Compound> getActiveCompoundsByTitle(@PathVariable int libraryId, @RequestParam("title") String title) {
        return compoundService.getActiveCompoundByTitle(libraryId, title);
    }

    @PutMapping("compounds/edit-compound")
    public Compound editAction(@RequestBody Compound compoundDTO) {
        return compoundService.editCompound(compoundDTO);
    }

    @PutMapping("compounds/change-status")
    public Compound changeStatus(@RequestBody ChangeCompoundStatusDTO changeCompoundStatusDTO) {
        return compoundService.changeStatus(changeCompoundStatusDTO.getStatusToChange(),
                changeCompoundStatusDTO.getId());
    }

    @DeleteMapping("compounds/delete-compound")
    public ResponseEntity<CompoundStatusResponse> deleteCompound(@RequestBody DeleteCompoundDTO deleteCompoundDTO) {
        compoundService.deleteCompound(deleteCompoundDTO);
        return ResponseEntity.ok(new CompoundStatusResponse("CompoundDeletedSuccessfully"));
    }
}
