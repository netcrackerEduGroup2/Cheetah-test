package com.ncedu.cheetahtest.dao.compound;

import com.ncedu.cheetahtest.entity.compound.Compound;

import java.util.List;

public interface CompoundDao {
    int createCompound(Compound compound);

    Compound findCompoundById(int id);

    List<Compound> findCompoundByIdTestScenario(int idTestScenario);

    List<Compound> selectAll();

    List<Compound> selectActiveCompoundByTitle(int idLibrary, String title);

    List<Compound> selectInactiveCompoundByTitle(int idLibrary, String title);

    Compound setTitle(String title, int id);

    Compound setDescription(String description, int id);

    Compound setTestScenarioId(String testScenarioId, int id);

    Compound editCompound(Compound compoundDTO);

    Compound setStatus(String status, int id);

    void removeCompoundById(int id);
}
