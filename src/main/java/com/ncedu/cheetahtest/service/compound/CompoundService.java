package com.ncedu.cheetahtest.service.compound;

import com.ncedu.cheetahtest.entity.compound.Compound;

import java.util.List;

public interface CompoundService {

    Compound createCompound(int idLibrary, Compound compoundDTO);
    List<Compound> selectAllCompound();
    Compound getCompoundId(int id);
    List<Compound> getActiveCompoundByTitle(int idLibrary, String title);
    List<Compound> getInactiveCompoundByTitle(int idLibrary, String title);
    Compound editCompound(Compound compoundDTO);
    Compound changeStatus(String status, int id);
    void deleteCompound(String token , int idCompound);
    boolean isStatusUnproper(String status);
}
