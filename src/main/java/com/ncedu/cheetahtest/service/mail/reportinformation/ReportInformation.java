package com.ncedu.cheetahtest.service.mail.reportinformation;


import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.actscenario.ActScenario;
import com.ncedu.cheetahtest.entity.historyacrion.HistoryAction;

import java.util.List;


public interface ReportInformation {

    String getUsernameByEmail(String email);

    String getTitleByProjectIdAndTestCaseId(int idProject, int idTestCase);

    String  getProjectTitleById(int idProject);

    boolean isRepeatableByIdTestCase(int idTestCase);

    List<ActScenario> getActScenariosByTestCaseId(int idTestCase);

    List<HistoryAction> getHistoryActionByTestHistoryId(int idHTC);

    Action getActionById(int id);

    String getExecutionDateByIdTestCase(int idTestCase);

}
