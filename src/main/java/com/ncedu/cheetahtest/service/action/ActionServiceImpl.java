package com.ncedu.cheetahtest.service.action;


import com.ncedu.cheetahtest.dao.action.ActionDao;
import com.ncedu.cheetahtest.dao.libactcompound.LibActCompoundDao;
import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.action.DeleteActionDTO;
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
    public void createAction(int idLibrary, Action actionDTO) {

        if (actionDTO.getTitle() == null || (!"active".equals(actionDTO.getStatus()) &&
                !"inactive".equals(actionDTO.getStatus())) ){
            throw new UnproperInputException();
        } else {
            int idAction = actionDao.createAction(actionDTO);
            LibActCompound insert = new LibActCompound();
            insert.setIdLibrary(idLibrary);
            insert.setIdAction(idAction);
            libActCompoundDao.createLibActCompound(insert);
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
        if ( !"active".equals(actionDTO.getStatus()) &&
                !"inactive".equals(actionDTO.getStatus())) {
            throw new UnproperInputException();
        } else {
            return actionDao.editAction(actionDTO);
        }

    }

    @Override
    public Action changeStatus(String status, int id) {
        if(!"active".equals(status) &&
                !"inactive".equals(status)){
            throw new UnproperInputException();
        }
        else return actionDao.setStatus(status,id);
    }



    @Override
    public void deleteAction(String token,DeleteActionDTO deleteActionDTO) {
        if (authService.isAdmin(token)){
            actionDao.removeActionById(deleteActionDTO.getId());
            libActCompoundDao.removeByActionId(deleteActionDTO.getId());
        }
        else throw new RightsPermissionException();

    }

    @Override
    public List<Action> getInactiveActionsByTitle(int idLibrary, String title) {
        return actionDao.getInactiveActionsByTitle(idLibrary,title);
    }
}
