package com.ncedu.cheetahtest.dao.user;

import com.ncedu.cheetahtest.entity.user.User;
import com.ncedu.cheetahtest.entity.user.UserRole;
import com.ncedu.cheetahtest.entity.user.UserStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {

    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String ROLE = "role";
    public static final String STATUS = "status";
    public static final String LAST_REQUEST = "last_request";
    public static final String PHOTO_URL = "photo_url";

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getInt(ID));
        user.setEmail(rs.getString(EMAIL));
        user.setPass(rs.getString(PASSWORD));
        user.setName(rs.getString(NAME));
        user.setRole(UserRole.valueOf(rs.getString(ROLE)));
        user.setStatus(UserStatus.valueOf(rs.getString(STATUS)));
        user.setLastRequest(rs.getTimestamp(LAST_REQUEST));
        user.setPhotoUrl(rs.getString(PHOTO_URL));

        return user;
    }
}