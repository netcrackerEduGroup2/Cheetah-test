package com.ncedu.cheetahtest.dao.compound;

import com.ncedu.cheetahtest.entity.compound.Compound;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CompoundRowMapper implements RowMapper<Compound> {
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String IDTESTSCENARIO = "id_test_scenario";
    public static final String STATUS = "status";

    @Override
    public Compound mapRow(ResultSet rs, int i) throws SQLException {
        Compound compound = new Compound();

        compound.setId(rs.getInt(ID));
        compound.setTitle(rs.getString(TITLE));
        compound.setDescription(rs.getString(DESCRIPTION));
        compound.setIdTestScenario(rs.getInt(IDTESTSCENARIO));
        compound.setStatus(rs.getString(STATUS));

        return compound;
    }
}
