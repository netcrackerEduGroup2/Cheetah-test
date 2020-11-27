package com.ncedu.cheetahtest.dao.actionparameters;

import com.ncedu.cheetahtest.entity.actionparameters.ActionParameter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActionParametersRowMapper implements RowMapper<ActionParameter> {
    public static final String ID = "id";
    public static final String PARAMETERSID = "parameters_id";

    @Override
    public ActionParameter mapRow(ResultSet resultSet, int i) throws SQLException {
        ActionParameter actionParameter = new ActionParameter();
        actionParameter.setId(resultSet.getInt(ID));
        actionParameter.setParametersId(resultSet.getInt(PARAMETERSID));
        return actionParameter;
    }
}
