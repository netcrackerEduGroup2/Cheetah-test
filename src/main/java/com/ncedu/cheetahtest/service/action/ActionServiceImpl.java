package com.ncedu.cheetahtest.service.action;


import com.ncedu.cheetahtest.dao.action.ActionDao;
import com.ncedu.cheetahtest.dao.libactcompound.LibActCompoundDao;
import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.action.DeleteActionDTO;
import com.ncedu.cheetahtest.entity.libactcompound.LibActCompound;
import com.ncedu.cheetahtest.exception.managelibraries.RightsPermissionException;
import com.ncedu.cheetahtest.exception.managelibraries.UnproperInputException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionServiceImpl implements ActionService {
    private final ActionDao actionDao;
    private final LibActCompoundDao libActCompoundDao;

    @Autowired
    public ActionServiceImpl(ActionDao actionDao, LibActCompoundDao libActCompoundDao) {

        this.actionDao = actionDao;
        this.libActCompoundDao = libActCompoundDao;
    }

    @Override
    public void createAction(int idLibrary, Action actionDTO) {

        if (actionDTO.getTitle() == null || (!actionDTO.getStatus().equals("active") &&
                !actionDTO.getStatus().equals("inactive"))) {
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
        if ( !actionDTO.getStatus().equals("active") &&
                !actionDTO.getStatus().equals("inactive")) {
            throw new UnproperInputException();
        } else {
            return actionDao.editAction(actionDTO);
        }

    }

    @Override
    public Action changeStatus(String status, int id) {
        if(!status.equals("active") &&
                !status.equals("inactive")){
            throw new UnproperInputException();
        }
        else return actionDao.setStatus(status,id);
    }

    @Override
    public boolean isAdmin(String jwtToken) {
        String[] split_string = jwtToken.split("\\.");
        String base64EncodedBody = split_string[1];
        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(base64EncodedBody));
        return body.contains("admin");

    }

    @Override
    public void deleteAction(String token,DeleteActionDTO deleteActionDTO) {
        if (isAdmin(token)){
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
