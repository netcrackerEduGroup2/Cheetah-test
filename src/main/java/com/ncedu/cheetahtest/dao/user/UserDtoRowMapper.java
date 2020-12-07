package com.ncedu.cheetahtest.dao.user;

import com.ncedu.cheetahtest.entity.user.UserDto;
import com.ncedu.cheetahtest.entity.user.UserRole;
import com.ncedu.cheetahtest.entity.user.UserStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDtoRowMapper implements RowMapper<UserDto> {

    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String ROLE = "role";
    public static final String STATUS = "status";

    @Override
    public UserDto mapRow(ResultSet rs, int i) throws SQLException {

        return new UserDto(rs.getInt(ID),rs.getString(EMAIL),
                rs.getString(NAME),UserRole.valueOf(rs.getString(ROLE)),
                UserStatus.valueOf(rs.getString(STATUS)));

    }
}
