package com.ncedu.cheetahtest.dao.action;


import com.ncedu.cheetahtest.entity.action.Action;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ActionRowMapper implements RowMapper<Action> {
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String IDCOMPOUND = "idCompound";
    public static final String IDTESTSCENARIO = "idTestScenario";
    public static final String STATUS = "status";

    @Override
    public Action mapRow(ResultSet rs, int i) throws SQLException {
        Action action = new Action();

        action.setId(rs.getInt(ID));
        action.setTitle(rs.getString(TITLE));
        action.setDescription(rs.getString(DESCRIPTION));
        action.setIdCompound(rs.getInt(IDCOMPOUND));
        action.setIdTestScenario(rs.getInt(IDTESTSCENARIO));
        action.setStatus(rs.getString(STATUS));

        return action;
    }
}
