package com.ncedu.cheetahtest.service.action;

import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.action.DeleteActionDTO;

import java.util.List;

public interface ActionService {

    void createAction(int idLibrary, Action actionDTO);
    List<Action> selectAllActions();
    Action getActionById(int id);
    List<Action> getActiveActionsByTitle(int idLibrary,String title);
    Action editAction(Action actionDTO);
    Action changeStatus(String status, int id);
    void deleteAction(String token, DeleteActionDTO deleteActionDTO);
    List<Action> getInactiveActionsByTitle(int idLibrary, String title);
}
