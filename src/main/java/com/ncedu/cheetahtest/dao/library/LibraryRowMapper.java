package com.ncedu.cheetahtest.dao.library;

import com.ncedu.cheetahtest.entity.library.Library;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryRowMapper implements RowMapper<Library> {
    public static final String ID = "id";
    public static final String DESCRIPTION = "description";
    public static final String NAME = "name";
    public static final String CREATEDATE = "create_date";

    @Override
    public Library mapRow(ResultSet rs, int i) throws SQLException {
        Library library = new Library();
        library.setId(rs.getInt(ID));
        library.setDescription(rs.getString(DESCRIPTION));
        library.setName(rs.getString(NAME));
        library.setCreateDate(rs.getDate(CREATEDATE));
        return library;
    }
}
