package com.ncedu.cheetahtest.dao.action;


import com.ncedu.cheetahtest.entity.action.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.PreparedStatementCallback;

import javax.sql.DataSource;
import java.util.List;

import static com.ncedu.cheetahtest.dao.action.ActionConsts.*;

@Repository
public class ActionDaoImpl implements ActionDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ActionDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Action> selectAll() {
        String sql = SELECT_ALL;
        return jdbcTemplate.query(
                sql,
                new ActionRowMapper());
    }

    @Override
    public List<Action> selectActiveActionsByTitle(int idLibrary,String title) {
        String sql = SELECT_ACTIVE_ACTIONS_BY_TITLE;
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2,idLibrary);
                },
                new ActionRowMapper());
    }

    @Override
    public List<Action> getInactiveActionsByTitle(int idLibrary, String title) {
        String sql = GET_INACTIVE_ACTIONS_BY_TITLE;
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2,idLibrary);
                },
                new ActionRowMapper());
    }

    @Override
    public int createAction(Action action) {
        String sql = CREATE_ACTION;
        jdbcTemplate.update(
                sql,
                action.getTitle(),
                action.getDescription(),
                action.getIdCompound(),
                action.getIdTestScenario(),
                action.getStatus()
        );
        sql = SELECT_CURRVAL_ACTION_ID;
        List<Integer> currIndex = jdbcTemplate.query(sql, new CurrentValueRowMapper());
        if(currIndex.size() == 1){
            return currIndex.get(0);
        }
        else return -1;
    }

    @Override
    public Action findActionById(int id) {
        String sql = FIND_ACTION_BY_ID;

        List<Action> actions = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new ActionRowMapper()
        );

        if (actions.size() == 1) {
            return actions.get(0);
        }
        return null;

    }

    @Override
    public List<Action> findActionsByIdCompound(int idCompound) {
        String sql = FIND_ACTIONS_BY_ID_COMPOUND;

        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idCompound),
                new ActionRowMapper()
        );
    }


    @Override
    public List<Action> findActionsByIdTestScenario(int idTestScenario) {
        String sql = FIND_ACTIONS_BY_ID_TESTSCENARIO;

        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idTestScenario),
                new ActionRowMapper()
        );

    }

    @Override
    public Action editAction(Action action) {
        String sql = EDIT_ACTION;
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, action.getTitle());
                    preparedStatement.setString(2, action.getDescription());
                    preparedStatement.setInt(3, action.getIdCompound());
                    preparedStatement.setInt(4, action.getIdTestScenario());
                    preparedStatement.setString(5, action.getStatus());
                    preparedStatement.setInt(6, action.getId());
                    return preparedStatement.execute();
                }
        );
        return action;

    }

    @Override
    public Action setTitle(String title, int id) {
        String sql = SET_TITLE;
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }

        );
        return this.findActionById(id);
    }

    @Override
    public Action setDescription(String description, int id) {
        String sql = SET_DESCRIPTION;
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, description);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }

        );
        return this.findActionById(id);
    }

    @Override
    public Action setCompoundId(String compId, int id) {
        String sql = SET_COMPOUND_ID;
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, compId);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }

        );
        return this.findActionById(id);

    }

    @Override
    public Action setTestScenarioId(String testScenarioId, int id) {
        String sql = SET_TESTSCENARIO_ID;
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, testScenarioId);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }

        );
       return this.findActionById(id);
    }

    @Override
    public Action setStatus(String status, int id) {
        String sql = SET_STATUS;
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, status);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }

        );
        return this.findActionById(id);
    }

    @Override
    public void removeActionById(int id) {
        String sql = REMOVE_ACTION_BY_ID;
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setInt(1, id);
                    return preparedStatement.execute();
                }
        );
    }
}
