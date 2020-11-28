package com.ncedu.cheetahtest.dao.actscenario;

import com.ncedu.cheetahtest.entity.actscenario.ActScenario;
import com.ncedu.cheetahtest.entity.actscenario.ActStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActScenarioRowMapper implements RowMapper<ActScenario> {
    public static final String ID ="s.id";
    public static final String ACTIONID ="s.action_id";
    public static final String TESTSCENARIOID = "s.test_scenario_id";
    public static final String PRIORITY = "s.priority";
    public static final String ACTSTATUS = "s.action_status";
    public static final String ACTPARAMID = "s.act_param_id";
    public static final String ACTTITLE = "a.title";
    public static final String ACTTYPE = "a.title";
    public static final String PARAMTYPE = "ap.title";
    public static final String PARAMVAL = "ap.title";


    @Override
    public ActScenario mapRow(ResultSet resultSet, int i) throws SQLException {
        ActScenario actScenario =new ActScenario();
        actScenario.setId(resultSet.getInt(ID));
        actScenario.setActionId(resultSet.getInt(ACTIONID));
        actScenario.setTestScenarioId(resultSet.getInt(TESTSCENARIOID));
        actScenario.setPriority(resultSet.getInt(PRIORITY));
        actScenario.setActStatus(ActStatus.valueOf(resultSet.getString(ACTSTATUS)));
        actScenario.setParameterId(resultSet.getInt(ACTPARAMID));
        actScenario.setActionTitle(resultSet.getString(ACTTITLE));
        actScenario.setActionType(resultSet.getString(ACTTYPE));
        actScenario.setParameterType(resultSet.getString(PARAMTYPE));
        actScenario.setParameterValue(resultSet.getString(PARAMVAL));
        return actScenario;

    }
}
