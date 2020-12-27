package com.ncedu.cheetahtest.service.mail.reportinformation;

import com.ncedu.cheetahtest.dao.action.ActionDao;
import com.ncedu.cheetahtest.dao.actscenario.ActScenarioDao;
import com.ncedu.cheetahtest.dao.hiatoryaction.HistoryActionDao;
import com.ncedu.cheetahtest.dao.project.ProjectDao;
import com.ncedu.cheetahtest.dao.testcase.TestCaseDao;
import com.ncedu.cheetahtest.dao.user.UserDao;
import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.actscenario.ActScenario;
import com.ncedu.cheetahtest.entity.historyacrion.HistoryAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ReportInformationImpl implements ReportInformation {

    private UserDao userDao;
    private TestCaseDao testCaseDao;
    private ProjectDao projectDao;
    private ActScenarioDao actScenarioDao;
    private ActionDao actionDao;
    private HistoryActionDao historyActionDao;

    @Autowired
    public ReportInformationImpl(UserDao userDao, TestCaseDao testCaseDao, ProjectDao projectDao,
                                 ActScenarioDao actScenarioDao, ActionDao actionDao,
                                 HistoryActionDao historyActionDao){
        this.userDao = userDao;
        this.testCaseDao = testCaseDao;
        this.projectDao = projectDao;
        this.actScenarioDao = actScenarioDao;
        this.actionDao = actionDao;
        this.historyActionDao = historyActionDao;
    }

    @Override
    public String getUsernameByEmail(String email){
        return userDao.findUserByEmail(email).getName();
    }

    @Override
    public String getTitleByProjectIdAndTestCaseId(int idProject, int idTestCase) {
        return testCaseDao.findTestCaseByProjectIdAndTestCaseId(idProject, idTestCase).getTitle();
    }

    @Override
    public String getProjectTitleById(int idProject) {
        return projectDao.findByProjectId(idProject).getTitle();
    }

    @Override
    public boolean isRepeatableByIdTestCase(int idTestCase) {
        return testCaseDao.getTestCaseRepeatable(idTestCase);
    }

    @Override
    public List<ActScenario> getActScenariosByTestCaseId(int idTestCase) {
        return actScenarioDao.getAllByTestCaseId(idTestCase);
    }

    @Override
    public List<HistoryAction> getHistoryActionByTestHistoryId(int idHTC) {
        return historyActionDao.getHistoryActionByTestHistoryId(idHTC);
    }

    @Override
    public Action getActionById(int id) {
        return actionDao.getActionById(id);
    }

    @Override
    public String getExecutionDateByIdTestCase(int idTestCase) {
        return  testCaseDao.getExecutionDateById(idTestCase);
    }
}
