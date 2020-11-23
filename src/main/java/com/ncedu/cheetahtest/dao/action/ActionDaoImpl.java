package com.ncedu.cheetahtest.dao.action;


import com.ncedu.cheetahtest.entity.action.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        return jdbcTemplate.query(
                SELECT_ALL,
                new ActionRowMapper());
    }

    @Override
    public List<Action> selectActiveActionsByTitle(int idLibrary,String title) {
        return jdbcTemplate.query(
                SELECT_ACTIVE_ACTIONS_BY_TITLE,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2,idLibrary);
                },
                new ActionRowMapper());
    }

    @Override
    public List<Action> getInactiveActionsByTitle(int idLibrary, String title) {
        return jdbcTemplate.query(
                GET_INACTIVE_ACTIONS_BY_TITLE,
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

        List<Action> actions = jdbcTemplate.query(
                FIND_ACTION_BY_ID,
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

        return jdbcTemplate.query(
                FIND_ACTIONS_BY_ID_COMPOUND,
                preparedStatement -> preparedStatement.setInt(1, idCompound),
                new ActionRowMapper()
        );
    }


    @Override
    public List<Action> findActionsByIdTestScenario(int idTestScenario) {

        return jdbcTemplate.query(
                FIND_ACTIONS_BY_ID_TESTSCENARIO,
                preparedStatement -> preparedStatement.setInt(1, idTestScenario),
                new ActionRowMapper()
        );

    }

    @Override
    public Action editAction(Action action) {
        jdbcTemplate.update(
                EDIT_ACTION,
                action.getTitle(),
                action.getDescription(),
                action.getIdCompound(),
                action.getIdTestScenario(),
                action.getStatus(),
                action.getId()
        );
        return action;

    }

    @Override
    public Action setTitle(String title, int id) {
        jdbcTemplate.update(
                SET_TITLE,
                title,
                id

        );
        return this.findActionById(id);
    }

    @Override
    public Action setDescription(String description, int id) {
        jdbcTemplate.update(
                SET_DESCRIPTION,
                description,
                id

        );
        return this.findActionById(id);
    }

    @Override
    public Action setCompoundId(String compId, int id) {
        jdbcTemplate.update(
                SET_COMPOUND_ID,
                compId,
                id

        );
        return this.findActionById(id);

    }

    @Override
    public Action setTestScenarioId(String testScenarioId, int id) {
        jdbcTemplate.update(
                SET_TESTSCENARIO_ID,
                testScenarioId,
                id

        );
       return this.findActionById(id);
    }

    @Override
    public Action setStatus(String status, int id) {
        jdbcTemplate.update(
                SET_STATUS,
                status,
                id

        );
        return this.findActionById(id);
    }

    @Override
    public void removeActionById(int id) {
        jdbcTemplate.update(
                REMOVE_ACTION_BY_ID,
                id
        );
    }
}
