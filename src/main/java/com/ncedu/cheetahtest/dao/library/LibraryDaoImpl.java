package com.ncedu.cheetahtest.dao.library;

import com.ncedu.cheetahtest.entity.library.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
        jdbcTemplate.update(
                CREATE_LIBRARY,
                library.getDescription(),
                library.getName(),
                library.getCreateDate()
        );
    }

    @Override
    public List<Library> selectAll() {
        return jdbcTemplate.query(SELECT_ALL,new LibraryRowMapper());
    }

    @Override
    public Library findLibraryById(int id) {
        List<Library> libraries = jdbcTemplate.query(
                FIND_LIBRARY_BY_ID,
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
        jdbcTemplate.update(
                SET_DESCRIPTION,
                description,
                id
        );
        return this.findLibraryById(id);

    }

    @Override
    public void removeLibrary(int id) {
        jdbcTemplate.update(
                REMOVE_LIBRARY,
                id
        );

    }

    @Override
    public List<Library> selectLibrariesByName(String name) {
        return jdbcTemplate.query(SELECT_LIBRARIES_BY_NAME,
                preparedStatement -> preparedStatement.setString(1,name),
                new LibraryRowMapper());
    }

    @Override
    public Library setName(String name, int id) {
        jdbcTemplate.update(
                SET_NAME,
                name,
                id
        );
        return this.findLibraryById(id);
    }
}
