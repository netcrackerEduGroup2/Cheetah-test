package com.ncedu.cheetahtest.dao.library;

import com.ncedu.cheetahtest.entity.library.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class LibraryDaoImpl implements LibraryDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LibraryDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createLibrary(Library library) {
        String sql = "INSERT INTO library (id , description, name , createdate) VALUES (?,?,?,?);";
        jdbcTemplate.update(
                sql,
                library.getId(),
                library.getDescription(),
                library.getName(),
                library.getCreateDate()
        );
    }

    @Override
    public List<Library> selectAll() {
        String sql = "SELECT id, description, name , createdate FROM library";
        return jdbcTemplate.query(sql,new LibraryRowMapper());
    }

    @Override
    public Library findLibraryById(int id) {
        String sql = "SELECT id, description, name , createdate FROM library WHERE id = ?";
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
    public void setDescription(String description, int id) {
        String sql = "UPDATE library SET description = ? WHERE id = ?;";
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, description);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }
        );

    }

    @Override
    public void removeLibrary(int id) {
        String sql = "DELETE FROM library WHERE id = ?";
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
        String sql = "SELECT id, description, name , createdate FROM library WHERE name LIKE CONCAT('%',?,'%')";
        return jdbcTemplate.query(sql,
                preparedStatement -> preparedStatement.setString(1,name),
                new LibraryRowMapper());
    }

    @Override
    public void setName(String name, int id) {
        String sql = "UPDATE library SET name = ? WHERE id = ?;";
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, name);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }
        );
    }
}
