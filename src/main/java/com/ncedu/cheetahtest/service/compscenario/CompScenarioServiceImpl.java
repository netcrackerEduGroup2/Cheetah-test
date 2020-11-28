package com.ncedu.cheetahtest.service.compscenario;

import com.ncedu.cheetahtest.dao.action.ActionDao;
import com.ncedu.cheetahtest.dao.actscenario.ActScenarioDao;
import com.ncedu.cheetahtest.dao.compound.CompoundDao;
import com.ncedu.cheetahtest.dao.compscenario.CompScenarioDao;
import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.actscenario.ActScenario;
import com.ncedu.cheetahtest.entity.actscenario.ActStatus;
import com.ncedu.cheetahtest.entity.compscenario.CompScenario;
import com.ncedu.cheetahtest.entity.compscenario.CompScenarioCreationDTO;
import com.ncedu.cheetahtest.exception.helpers.UnproperInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompScenarioServiceImpl implements CompScenarioService {
    private final CompScenarioDao compScenarioDao;
    private final ActScenarioDao actScenarioDao;
    private final CompoundDao compoundDao;
    private final ActionDao actionDao;

    @Autowired
    public CompScenarioServiceImpl(CompScenarioDao compScenarioDao, ActScenarioDao actScenarioDao,
                                   CompoundDao compoundDao, ActionDao actionDao) {
        this.compScenarioDao = compScenarioDao;
        this.actScenarioDao = actScenarioDao;
        this.compoundDao = compoundDao;
        this.actionDao = actionDao;
    }


    @Override
    public CompScenario createCompScenario(CompScenarioCreationDTO compScenarioCreationDTO) {
        //list of id`s of parameters for future act_scenarios
        List<Integer> idParams = compScenarioCreationDTO.getIdParams();
        //created new comp_scenario (now it is raw without act_scenarios in it)
        CompScenario compScenario = compScenarioDao.createCompScenario(compScenarioCreationDTO.getCompScenario());
        List<Action> actions = actionDao.getAllActionsInComp(compScenario.getIdCompound());
        if(actions.size()!=idParams.size()) throw new UnproperInputException();
        //now we have list of actions from which we will do our act_scenarios
        //it is mandatory for parameters to come in same order, as order in compound!
        ActScenario actScenario = new ActScenario();
        int priority = 1;
        int index = 0;
        while (index < idParams.size()) {
            //now we are creating act_scenarios, constructing them from parts
            actScenario.setActionId(actions.get(index).getId());
            actScenario.setTestScenarioId(compScenario.getIdTestScenario());
            actScenario.setPriority(priority);
            actScenario.setParameterId(idParams.get(index));
            actScenarioDao.createActScenario(actScenario);
            index++;
            priority++;
        }
        //returning created comp_scenario which already has it`s act_scenarios made
        return compScenario;
    }

    @Override
    public CompScenario editCompScenario(CompScenarioCreationDTO compScenarioCreationDTO, int id) {
        List<Integer> idParams = compScenarioCreationDTO.getIdParams();

        CompScenario compScenario = compScenarioDao.editCompScenario(compScenarioCreationDTO.getCompScenario(),id);
        List<Action> actions = actionDao.getAllActionsInComp(compScenario.getIdCompound());

        actScenarioDao.deleteAllByIdCompScenario(id);

        ActScenario actScenario = new ActScenario();
        int priority = 1;
        int index = 0;
        while (index < idParams.size()) {
            //now we are creating act_scenarios, constructing them from parts
            actScenario.setActionId(actions.get(index).getId());
            actScenario.setTestScenarioId(compScenario.getIdTestScenario());
            actScenario.setPriority(priority);
            actScenario.setParameterId(idParams.get(index));
            actScenarioDao.createActScenario(actScenario);
            index++;
            priority++;
        }
        //returning created comp_scenario which already has it`s act_scenarios made
        return compScenario;
    }

    @Override
    public List<CompScenario> findByTitleLike(String title, int size, int page) {
        return null;
    }

    @Override
    public List<CompScenario> findByTitleInTestScenario(String title, int idTestScenario, int size, int page) {
        return null;
    }

    @Override
    public List<CompScenario> findAllByTitleLike(String title) {
        return null;
    }

    @Override
    public List<CompScenario> findAllByTitleInTestScenario(String title, int idTestScenario) {
        return null;
    }

    @Override
    public CompScenario editCompScenarioProps(CompScenario compScenario, int id) {
        return null;
    }


    @Override
    public void setStatusForAllByIdTestScenario(ActStatus actStatus, int idTestScenario) {

    }

    @Override
    public List<ActScenario> getAllActionScenarioInComp(int id) {
        return null;
    }

    @Override
    public void deleteCompScenario(int id) {

    }


}
