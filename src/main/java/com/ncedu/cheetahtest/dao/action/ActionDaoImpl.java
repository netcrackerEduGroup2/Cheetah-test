package com.ncedu.cheetahtest.dao.action;


import com.ncedu.cheetahtest.entity.action.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.PreparedStatementCallback;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ActionDaoImpl implements ActionDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ActionDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createAction(Action action) {
        String sql = "INSERT INTO actions (title , description , idcompound , idtestscenario, status) " +
                "VALUES (?, ?, ?, ?, ?::action_status);";
        jdbcTemplate.update(
                sql,
                action.getTitle(),
                action.getDescription(),
                action.getIdCompound(),
                action.getIdTestScenario(),
                action.getStatus()
        );
    }

    @Override
    public Action findActionById(int id) {
        String sql = "SELECT id, title , description , idcompound , idtestscenario, status " +
                "FROM actions " +
                "WHERE id = ?";

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
        String sql = "SELECT id, title , description , idcompound , idtestscenario, status " +
                "FROM actions " +
                "WHERE idcompound = ?";

        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idCompound),
                new ActionRowMapper()
        );
    }

    @Override
    public List<Action> findActionsByIdTestScenario(int idTestScenario) {
        String sql = "SELECT id, title , description , idcompound , idtestscenario, status " +
                "FROM actions " +
                "WHERE idtestscenario = ?";

        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idTestScenario),
                new ActionRowMapper()
        );


    }

    @Override
    public Action findActionByTitle(String title) {
        String sql = "SELECT id, title , description , idcompound , idtestscenario, status " +
                "FROM actions " +
                "WHERE title = ?";

        List<Action> actions = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, title),
                new ActionRowMapper()
        );

        if (actions.size() == 1) {
            return actions.get(0);
        }
        return null;
    }

    @Override
    public void setTitle(String title, int id) {
        String sql = "UPDATE actions SET title = ? WHERE id = ?";
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }

        );
    }

    @Override
    public void setDescription(String description, int id) {
        String sql = "UPDATE actions SET description = ? WHERE id = ?";
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
    public void setCompoundId(String compId, int id) {
        String sql = "UPDATE actions SET idCompound = ? WHERE id = ?";
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, compId);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }

        );

    }

    @Override
    public void setTestScenarioId(String testScenarioId, int id) {
        String sql = "UPDATE actions SET idTestScenario = ? WHERE id = ?";
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, testScenarioId);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }

        );
    }

    @Override
    public void setStatus(String status, int id) {
        String sql = "UPDATE actions SET status = ? WHERE id = ?";
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, status);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }

        );
    }

    @Override
    public void removeActionById(int id) {
        String sql = "DELETE FROM actions WHERE id = ?";
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setInt(1, id);
                    return preparedStatement.execute();
                }
        );
    }
}
