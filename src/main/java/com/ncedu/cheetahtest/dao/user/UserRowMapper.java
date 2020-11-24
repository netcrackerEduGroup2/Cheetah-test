package com.ncedu.cheetahtest.dao.user;

import com.ncedu.cheetahtest.entity.user.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String ROLE = "role";
    public static final String STATUS = "status";
    public static final String RESET_TOKEN_ID = "reset_token_id";
    public static final String LAST_REQUEST = "last_request";

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getInt(ID));
        user.setEmail(rs.getString(EMAIL));
        user.setPass(rs.getString(PASSWORD));
        user.setName(rs.getString(NAME));
        user.setRole(rs.getString(ROLE));
        user.setStatus(rs.getString(STATUS));
        user.setResetPassToken(rs.getInt(RESET_TOKEN_ID));
        user.setLastRequest(rs.getTimestamp(LAST_REQUEST));

        return user;
    }
}