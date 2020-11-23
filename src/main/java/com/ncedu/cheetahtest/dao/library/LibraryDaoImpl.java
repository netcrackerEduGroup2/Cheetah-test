package com.ncedu.cheetahtest.dao.library;

import com.ncedu.cheetahtest.entity.library.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.ncedu.cheetahtest.dao.library.LIbraryConsts.*;

@Repository
public class LibraryDaoImpl implements LibraryDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LibraryDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createLibrary(Library library) {
        String sql = CREATE_LIBRARY;
        jdbcTemplate.update(
                sql,
                library.getDescription(),
                library.getName(),
                library.getCreateDate()
        );
    }

    @Override
    public List<Library> selectAll() {
        String sql = SELECT_ALL;
        return jdbcTemplate.query(sql,new LibraryRowMapper());
    }

    @Override
    public Library findLibraryById(int id) {
        String sql = FIND_LIBRARY_BY_ID;
        List<Library> libraries = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new LibraryRowMapper()
        );
        if (libraries.size() == 1) {
            return libraries.get(0);
        }
        return null;
    }

    @Override
    public Library setDescription(String description, int id) {
        String sql = SET_DESCRIPTION;
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, description);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }
        );
        return this.findLibraryById(id);

    }

    @Override
    public void removeLibrary(int id) {
        String sql = REMOVE_LIBRARY;
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setInt(1, id);
                    return preparedStatement.execute();
                }
        );

    }

    @Override
    public List<Library> selectLibrariesByName(String name) {
        String sql = SELECT_LIBRARIES_BY_NAME;
        return jdbcTemplate.query(sql,
                preparedStatement -> preparedStatement.setString(1,name),
                new LibraryRowMapper());
    }

    @Override
    public Library setName(String name, int id) {
        String sql = SET_NAME;
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, name);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }
        );
        return this.findLibraryById(id);
    }
}
