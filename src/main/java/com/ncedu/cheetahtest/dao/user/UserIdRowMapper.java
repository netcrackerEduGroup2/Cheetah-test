package com.ncedu.cheetahtest.dao.user;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserIdRowMapper implements RowMapper<Integer> {
    public static final String ID = "id";

    @Override
    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getInt(ID);
    }
}
