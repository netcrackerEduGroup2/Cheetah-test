package com.ncedu.cheetahtest.dao.libactcompound;

import com.ncedu.cheetahtest.entity.libactcompound.LibActCompound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
        jdbcTemplate.update(
                CREATE_LIBACTCOMPOUND,
                libActCompound.getIdLibrary(),
                libActCompound.getIdCompound(),
                libActCompound.getIdAction()
        );
    }

    @Override
    public List<LibActCompound> findLibActCompoundsById(int id) {
        return jdbcTemplate.query(
                FIND_LIBACTCOMPOUNDS_BY_ID,
                preparedStatement -> preparedStatement.setInt(1, id),
                new LibActionCompoundRowMapper()
        );


    }

    @Override
    public List<LibActCompound> findLibActCompoundsByIdCompound(int id) {
        return jdbcTemplate.query(
                FIND_LIBACTCOMPOUNDS_BY_ID_COMPOUND,
                preparedStatement -> preparedStatement.setInt(1, id),
                new LibActionCompoundRowMapper()
        );
    }

    @Override
    public List<LibActCompound> findLibActCompoundsByIdAct(int id) {
        return jdbcTemplate.query(
                FIND_LIBACTCOMPOUNDS_BY_ID_ACT,
                preparedStatement -> preparedStatement.setInt(1, id),
                new LibActionCompoundRowMapper()
        );
    }

    @Override
    public void setIdCompound(int idCompound, int id) {
        jdbcTemplate.update(
                SET_ID_COMPOUND,
                idCompound,
                id

        );
    }

    @Override
    public void setIdAction(int idAction, int id) {
        jdbcTemplate.update(
                SET_ID_ACTION,
                idAction,
                id

        );
    }

    @Override
    public void removeByLibraryId(int idLib) {
        jdbcTemplate.update(
                REMOVE_BY_LIBRARY_ID,
                idLib
        );
    }

    @Override
    public void removeByCompoundId(int idComp) {
        jdbcTemplate.update(
                REMOVE_BY_COMPOUND_ID,
                idComp
        );
    }

    @Override
    public void removeByActionId(int idAct) {
        jdbcTemplate.update(
                REMOVE_BY_ACTION_ID,
                idAct
        );
    }
}
