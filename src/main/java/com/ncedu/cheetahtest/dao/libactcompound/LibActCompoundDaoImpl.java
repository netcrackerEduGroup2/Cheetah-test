package com.ncedu.cheetahtest.dao.libactcompound;

import com.ncedu.cheetahtest.entity.libActCompound.LibActCompound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.ncedu.cheetahtest.dao.libactcompound.LibActCompoundConsts.*;

@Repository
public class LibActCompoundDaoImpl implements LibActCompoundDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LibActCompoundDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createLibActCompound(LibActCompound libActCompound) {
        String sql = CREATE_LIBACTCOMPOUND;
        jdbcTemplate.update(
                sql,
                libActCompound.getIdLibrary(),
                libActCompound.getIdCompound(),
                libActCompound.getIdAction()
        );
    }

    @Override
    public List<LibActCompound> findLibActCompoundsById(int id) {
        String sql = FIND_LIBACTCOMPOUNDS_BY_ID;
         return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new LibActionCompoundRowMapper()
        );


    }

    @Override
    public List<LibActCompound> findLibActCompoundsByIdCompound(int id) {
        String sql = FIND_LIBACTCOMPOUNDS_BY_ID_COMPOUND;
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new LibActionCompoundRowMapper()
        );
    }

    @Override
    public List<LibActCompound> findLibActCompoundsByIdAct(int id) {
        String sql = FIND_LIBACTCOMPOUNDS_BY_ID_ACT;
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new LibActionCompoundRowMapper()
        );
    }

    @Override
    public void setIdCompound(int idCompound, int id) {
        String sql = SET_ID_COMPOUND;
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setInt(1, idCompound);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }

        );
    }

    @Override
    public void setIdAction(int idAction, int id) {
        String sql = SET_ID_ACTION;
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setInt(1, idAction);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }

        );
    }

    @Override
    public void removeByLibraryId(int idLib) {
        String sql = REMOVE_BY_LIBRARY_ID;
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setInt(1, idLib);
                    return preparedStatement.execute();
                }
        );
    }

    @Override
    public void removeByCompoundId(int idComp) {
        String sql = REMOVE_BY_COMPOUND_ID;
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setInt(1, idComp);
                    return preparedStatement.execute();
                }
        );
    }

    @Override
    public void removeByActionId(int idAct) {
        String sql = REMOVE_BY_ACTION_ID;
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setInt(1, idAct);
                    return preparedStatement.execute();
                }
        );
    }
}
