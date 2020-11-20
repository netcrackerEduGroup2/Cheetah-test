package com.ncedu.cheetahtest.dao.action;


import com.ncedu.cheetahtest.entity.action.Action;

import java.util.List;

public interface ActionDao {
    void createAction(Action action);

    Action findActionById(int id);

    Action findActionByTitle(String title);

    List<Action> findActionsByIdCompound(int idCompound);

    List<Action> findActionsByIdTestScenario(int idTestScenario);

    void setTitle(String title, int id);

    void setDescription(String description, int id);

    void setCompoundId(String compId, int id);

    void setTestScenarioId(String testScenarioId, int id);

    void setStatus(String status, int id);

    void removeActionById(int id);

}
