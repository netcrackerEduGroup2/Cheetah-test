package com.ncedu.cheetahtest.developer.dao;

import com.ncedu.cheetahtest.developer.entity.Developer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeveloperRowMapper implements RowMapper<Developer> {

    public static final String ID = "id";
    public static final String LOGIN = "login";
    public static final String PASS = "pass";
    public static final String NAME = "name";
    public static final String ROLE = "role";
    public static final String STATUS = "status";
    public static final String ID_RESET_TOKEN = "id_reset_token";

    @Override
    public Developer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Developer employee = new Developer();

        employee.setId(rs.getInt(ID));
        employee.setLogin(rs.getString(LOGIN));
        employee.setPass(rs.getString(PASS));
        employee.setName(rs.getString(NAME));
        employee.setType(rs.getString(ROLE));
        employee.setStatus(rs.getString(STATUS));
        employee.setResetPassToken(rs.getInt(ID_RESET_TOKEN));

        return employee;
    }
}