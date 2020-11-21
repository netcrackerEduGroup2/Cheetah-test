package com.ncedu.cheetahtest.service.action;


import com.ncedu.cheetahtest.dao.action.ActionDao;
import com.ncedu.cheetahtest.dao.libActCompound.LibActCompoundDao;
import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.libActCompound.LibActCompound;
import com.ncedu.cheetahtest.exception.manageLibraries.UnproperInputException;
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
    public List<Action> getActionsByTitle(int idLibrary,String title) {
        System.out.println(idLibrary);
        System.out.println(title);
        return actionDao.selectActionsByTitle(idLibrary,title);
    }

    @Override
    public void editAction(Action actionDTO) {
        actionDao.editAction(actionDTO);
    }

    @Override
    public void changeStatus(String status, int id) {
        if(!status.equals("active") &&
                !status.equals("inactive")){
            throw new UnproperInputException();
        }
        else actionDao.setStatus(status,id);
    }
}
