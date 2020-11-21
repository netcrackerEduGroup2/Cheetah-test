package com.ncedu.cheetahtest.controller.compound;

import com.ncedu.cheetahtest.entity.compound.Compound;
import com.ncedu.cheetahtest.entity.compound.CreateCompoundResponse;
import com.ncedu.cheetahtest.service.compound.CompoundService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")

public class ManageCompoundController {
    private final CompoundService compoundService;

    @Autowired

    public ManageCompoundController(CompoundService compoundService) {
        this.compoundService = compoundService;
    }

    @PostMapping("/create_compound")
    public  ResponseEntity<CreateCompoundResponse> createCompound(@RequestBody Compound compoundDTO) {
        compoundService.createCompound(compoundDTO);
        return ResponseEntity.ok(new CreateCompoundResponse("Success"));

    }

}
