package com.ncedu.cheetahtest.dao.action;


import com.ncedu.cheetahtest.entity.action.Action;

import java.util.List;

public interface ActionDao {

    List<Action> selectActionsByTitleLike(String title,int limit, int offset);
    int getTotalElements(String title);
    List<Action> getActionsInCompound(int idCompound, int limit, int offset);
    int getTotalActionsInComp(int idCompound);
    Action getActionByTitle(String title);
    Action getActionById(int id);
    Action editActionDesc(String description, int id);

}
