package com.ncedu.cheetahtest.dao.library;

import com.ncedu.cheetahtest.entity.library.Library;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryRowMapper implements RowMapper<Library> {
    public static final String ID = "id";
    public static final String DESCRIPTION = "description";

    @Override
    public Library mapRow(ResultSet rs, int i) throws SQLException {
        Library library = new Library();
        library.setId(rs.getInt(ID));
        library.setDescription(rs.getString(DESCRIPTION));
        return library;
    }
}
