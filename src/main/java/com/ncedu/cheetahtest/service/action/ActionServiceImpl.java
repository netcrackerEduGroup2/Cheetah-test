package com.ncedu.cheetahtest.service.action;

import com.ncedu.cheetahtest.dao.action.ActionDao;
import com.ncedu.cheetahtest.dao.libactcompound.LibActCompoundDao;
import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.libactcompound.LibActCompound;
import com.ncedu.cheetahtest.exception.managelibraries.RightsPermissionException;
import com.ncedu.cheetahtest.exception.managelibraries.UnproperInputException;
import com.ncedu.cheetahtest.service.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionServiceImpl implements ActionService {
    private final ActionDao actionDao;
    private final LibActCompoundDao libActCompoundDao;
    private final AuthService authService;

    @Autowired
    public ActionServiceImpl(ActionDao actionDao, LibActCompoundDao libActCompoundDao, AuthService authService) {

        this.actionDao = actionDao;
        this.libActCompoundDao = libActCompoundDao;
        this.authService = authService;
    }

    @Override
    public Action createAction(int idLibrary, Action actionDTO) {

        if (actionDTO.getTitle() == null || isStatusUnProper(actionDTO.getStatus()) ){
            throw new UnproperInputException();
        } else {
            Action createdAction = actionDao.createAction(actionDTO);
            LibActCompound insert = new LibActCompound();
            insert.setIdLibrary(idLibrary);
            insert.setIdAction(createdAction.getId());
            libActCompoundDao.createLibActCompound(insert);
            return createdAction;
        }

    }

    @Override
    public List<Action> selectAllActions() {
        return actionDao.selectAll();
    }

    @Override
    public Action getActionById(int id) {
        return actionDao.findActionById(id);
    }

    @Override
    public List<Action> getActiveActionsByTitle(int idLibrary,String title) {
        return actionDao.selectActiveActionsByTitle(idLibrary,title);
    }

    @Override
    public Action editAction(Action actionDTO) {
        if (isStatusUnProper(actionDTO.getStatus())) {
            throw new UnproperInputException();
        } else {
            return actionDao.editAction(actionDTO);
        }

    }

    @Override
    public Action changeStatus(String status, int id) {
        if(isStatusUnProper(status)){
            throw new UnproperInputException();
        }
        else return actionDao.setStatus(status,id);
    }



    @Override
    public void deleteAction(String token,int idAction) {
        if (authService.isAdmin(token)){
            actionDao.removeActionById(idAction);
            libActCompoundDao.removeByActionId(idAction);
        }
        else throw new RightsPermissionException();

    }

    @Override
    public List<Action> getInactiveActionsByTitle(int idLibrary, String title) {
        return actionDao.getInactiveActionsByTitle(idLibrary,title);
    }

    @Override
    public boolean isStatusUnProper(String status) {
        return !"active".equals(status) &&
                !"inactive".equals(status);
    }
}
