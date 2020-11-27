package com.ncedu.cheetahtest.service.action;

import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.action.PaginationAction;

public interface ActionService {

    PaginationAction getActionsByTitle(String title, int size, int page);
    PaginationAction getActionsInCompound(int idCompound,int size, int page);
    Action editActionDescription(String description,int id);
}
