package com.ncedu.cheetahtest.dao.libactcompound;

import com.ncedu.cheetahtest.entity.libactcompound.LibActCompound;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LibActionCompoundRowMapper implements RowMapper<LibActCompound> {
    public static final String ID_LIBRARY = "id_library";
    public static final String ID_COMPOUND = "id_compound";
    public static final String ID_ACTION = "id_action";

    @Override
    public LibActCompound mapRow(ResultSet rs, int i) throws SQLException {
        LibActCompound libActCompound = new LibActCompound();
        libActCompound.setIdLibrary(rs.getInt(ID_LIBRARY));
        libActCompound.setIdCompound(rs.getInt(ID_COMPOUND));
        libActCompound.setIdAction(rs.getInt(ID_ACTION));
        return libActCompound;
    }
}
