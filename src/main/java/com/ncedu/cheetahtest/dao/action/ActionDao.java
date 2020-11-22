package com.ncedu.cheetahtest.dao.action;


import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.action.ActionDTO;

import java.util.List;

public interface ActionDao {
    int createAction(Action action);

    Action findActionById(int id);

    List<Action> findActionsByIdCompound(int idCompound);

    List<Action> findActionsByIdTestScenario(int idTestScenario);

    List<Action> selectAll();

    List<Action> selectActiveActionsByTitle(int idLibrary,String title);

    void setTitle(String title, int id);

    void setDescription(String description, int id);

    void setCompoundId(String compId, int id);

    void setTestScenarioId(String testScenarioId, int id);

    void editAction(Action actionDTO);

    void setStatus(String status, int id);

    void removeActionById(int id);

    List<Action> getInactiveActionsByTitle(int idLibrary,String title);

}
