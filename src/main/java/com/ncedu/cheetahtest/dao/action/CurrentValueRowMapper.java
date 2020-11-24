package com.ncedu.cheetahtest.dao.action;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CurrentValueRowMapper implements RowMapper<Integer> {
    private static final String ID = "currval";

    @Override
    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getInt(ID);
    }
}
