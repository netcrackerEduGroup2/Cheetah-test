package com.ncedu.cheetahtest.service.action;

import com.ncedu.cheetahtest.entity.action.Action;

public interface ActionService {
    public void createAction(String accessToken, Action actionDTO);
}
