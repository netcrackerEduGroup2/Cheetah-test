package com.ncedu.cheetahtest.dao.dashboard;

import com.ncedu.cheetahtest.entity.dashboard.ProjectsSuppListDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SuppProjectsDTOMapper implements RowMapper<ProjectsSuppListDTO> {
    public static final String ID = "id";
    public static final String TITLE = "title";

    @Override
    public ProjectsSuppListDTO mapRow(ResultSet rs, int i) throws SQLException {
        ProjectsSuppListDTO projectsSuppListDTO = new ProjectsSuppListDTO();

        projectsSuppListDTO.setId(rs.getInt(ID));
        projectsSuppListDTO.setTitle(rs.getString(TITLE));

        return projectsSuppListDTO;
    }
}
