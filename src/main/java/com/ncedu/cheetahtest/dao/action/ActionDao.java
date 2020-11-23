package com.ncedu.cheetahtest.dao.action;


import com.ncedu.cheetahtest.entity.action.Action;

import java.util.List;

public interface ActionDao {
    int createAction(Action action);

    Action findActionById(int id);

    List<Action> findActionsByIdCompound(int idCompound);

    List<Action> findActionsByIdTestScenario(int idTestScenario);

    List<Action> selectAll();

    List<Action> selectActiveActionsByTitle(int idLibrary,String title);

    Action setTitle(String title, int id);

    Action setDescription(String description, int id);

    Action setCompoundId(String compId, int id);

    Action setTestScenarioId(String testScenarioId, int id);

    Action editAction(Action actionDTO);

    Action setStatus(String status, int id);

    void removeActionById(int id);

    List<Action> getInactiveActionsByTitle(int idLibrary,String title);

}
