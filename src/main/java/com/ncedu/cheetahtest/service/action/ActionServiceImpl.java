package com.ncedu.cheetahtest.service.action;


import com.ncedu.cheetahtest.dao.action.ActionDao;
import com.ncedu.cheetahtest.entity.action.Action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionServiceImpl implements ActionService {
    private final ActionDao actionDao;

    @Autowired
    public ActionServiceImpl(ActionDao actionDao) {
        this.actionDao = actionDao;
    }

    @Override
    public void createAction(Action actionDTO) {

        actionDao.createAction(actionDTO);
    }
}
