package com.ncedu.cheetahtest.dao.dashboard;

import com.ncedu.cheetahtest.entity.dashboard.UserProjectsDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProjectsDTOMapper implements RowMapper<UserProjectsDTO> {
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String USER_STATUS = "user_status";

    @Override
    public UserProjectsDTO mapRow(ResultSet rs, int i) throws SQLException {
        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();

        userProjectsDTO.setId(rs.getInt(ID));
        userProjectsDTO.setTitle(rs.getString(TITLE));
        userProjectsDTO.setUserStatus(rs.getString(USER_STATUS));

        return userProjectsDTO;
    }
}
