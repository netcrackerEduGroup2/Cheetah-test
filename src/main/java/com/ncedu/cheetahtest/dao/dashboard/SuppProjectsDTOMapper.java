package com.ncedu.cheetahtest.dao.dashboard;

import com.ncedu.cheetahtest.entity.dashboard.SuppProjectsDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SuppProjectsDTOMapper implements RowMapper<SuppProjectsDTO> {
    public static final String ID = "id";
    public static final String TITLE = "title";

    @Override
    public SuppProjectsDTO mapRow(ResultSet rs, int i) throws SQLException {
        SuppProjectsDTO suppProjectsDTO = new SuppProjectsDTO();

        suppProjectsDTO.setId(rs.getInt(ID));
        suppProjectsDTO.setTitle(rs.getString(TITLE));

        return suppProjectsDTO;
    }
}
