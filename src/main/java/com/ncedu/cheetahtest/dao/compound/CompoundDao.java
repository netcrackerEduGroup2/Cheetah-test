package com.ncedu.cheetahtest.dao.compound;

import com.ncedu.cheetahtest.entity.compound.Compound;

import java.util.List;

public interface CompoundDao {
    int createCompound(Compound compound);

    Compound findCompoundById(int id);

    List<Compound> findCompoundByIdTestScenario(int idTestScenario);

    List<Compound> selectAll();

    List<Compound> selectCompoundByTitle(int idLibrary, String tittle);

    void setTitle(String title, int id);

    void setDescription(String description, int id);

    void setTestScenarioId(String testScenarioId, int id);

    void editCompound(Compound compoundDTO);

    void setStatus(String status, int id);

    void removeCompoundById(int id);
}
