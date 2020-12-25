package com.ncedu.cheetahtest.dao.dashboard;

import com.ncedu.cheetahtest.entity.dashboard.UserActivityDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserActivityMapper implements RowMapper<UserActivityDTO> {

    public static final String NAME = "name";
    public static final String ROLE = "role";
    public static final String LAST_REQUEST = "last_request";
    public static final String PHOTO_URL = "photo_url";

    @Override
    public UserActivityDTO mapRow(ResultSet rs, int i) throws SQLException {
        UserActivityDTO userActivityDTO = new UserActivityDTO();

        userActivityDTO.setName(rs.getString(NAME));
        userActivityDTO.setRole(rs.getString(ROLE));
        userActivityDTO.setTime(rs.getString(LAST_REQUEST));
        userActivityDTO.setPhotoUrl(rs.getString(PHOTO_URL));

        return userActivityDTO;
    }
}
