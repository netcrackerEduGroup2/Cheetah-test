package com.ncedu.cheetahtest.developer.dao;

import com.ncedu.cheetahtest.developer.entity.Developer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeveloperRowMapper implements RowMapper<Developer> {

    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String ROLE = "role";
    public static final String STATUS = "status";
    public static final String RESET_TOKEN_ID = "reset_token_id";

    @Override
    public Developer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Developer developer = new Developer();

        developer.setId(rs.getInt(ID));
        developer.setEmail(rs.getString(EMAIL));
        developer.setPass(rs.getString(PASSWORD));
        developer.setName(rs.getString(NAME));
        developer.setRole(rs.getString(ROLE));
        developer.setStatus(rs.getString(STATUS));
        developer.setResetPassToken(rs.getInt(RESET_TOKEN_ID));

        return developer;
    }
}