package com.ncedu.cheetahtest.service.action;

import com.ncedu.cheetahtest.entity.action.Action;

import java.util.List;

public interface ActionService {

    void createAction(int idLibrary, Action actionDTO);
    List<Action> selectAllActions();
    Action getActionById(int id);
    List<Action> getActionsByTitle(int idLibrary,String title);
    void editAction(Action actionDTO);
    void changeStatus(String status, int id);
}
