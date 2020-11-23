package com.ncedu.cheetahtest.dao.libactcompound;

import com.ncedu.cheetahtest.entity.libActCompound.LibActCompound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class LibActCompoundDaoImpl implements LibActCompoundDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LibActCompoundDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createLibActCompound(LibActCompound libActCompound) {
        String sql = "INSERT INTO lib_act_compound (id_library , id_compound , id_action) " +
                "VALUES (?, ?, ?);";
        jdbcTemplate.update(
                sql,
                libActCompound.getIdLibrary(),
                libActCompound.getIdCompound(),
                libActCompound.getIdAction()
        );
    }

    @Override
    public List<LibActCompound> findLibActCompoundsById(int id) {
        String sql = "SELECT id, id_library , id_compound , id_action FROM lib_act_compound " +
                "WHERE id_library = ?";
         return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new LibActionCompoundRowMapper()
        );


    }

    @Override
    public List<LibActCompound> findLibActCompoundsByIdCompound(int id) {
        String sql = "SELECT id, id_library , id_compound , id_action FROM lib_act_compound " +
                "WHERE id_compound = ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new LibActionCompoundRowMapper()
        );
    }

    @Override
    public List<LibActCompound> findLibActCompoundsByIdAct(int id) {
        String sql = "SELECT id, id_library , id_compound , id_action FROM lib_act_compound " +
                "WHERE id_action = ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new LibActionCompoundRowMapper()
        );
    }

    @Override
    public void setIdCompound(int idCompound, int id) {
        String sql = "UPDATE lib_act_compound SET id_compound = ? WHERE id = ?";
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
        String sql = "UPDATE lib_act_compound SET id_action = ? WHERE id = ?";
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
        String sql = "DELETE from lib_act_compound WHERE id_library = ?";
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
        String sql = "DELETE from lib_act_compound WHERE id_compound = ?";
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
        String sql = "DELETE from lib_act_compound WHERE id_action = ?";
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setInt(1, idAct);
                    return preparedStatement.execute();
                }
        );
    }
}
