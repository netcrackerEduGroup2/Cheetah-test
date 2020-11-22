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
    public List<Action> selectAll() {
        String sql = "SELECT id,title , description , idcompound , idtestscenario, status FROM actions";
        return jdbcTemplate.query(
                sql,
                new ActionRowMapper());
    }

    @Override
    public List<Action> selectActiveActionsByTitle(int idLibrary,String title) {
        String sql = "SELECT actions.id, actions.title , actions.description , actions.idcompound , actions.idtestscenario, " +
                "actions.status FROM actions INNER JOIN lib_act_compound ON actions.id = lib_act_compound.id_action " +
                "INNER JOIN library ON lib_act_compound.id_library = library.id " +
                "WHERE actions.title LIKE CONCAT('%',?,'%') AND library.id = ? AND actions.status ='active'";
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
        String sql = "SELECT actions.id, actions.title , actions.description , actions.idcompound , actions.idtestscenario, " +
                "actions.status FROM actions INNER JOIN lib_act_compound ON actions.id = lib_act_compound.id_action " +
                "INNER JOIN library ON lib_act_compound.id_library = library.id " +
                "WHERE actions.title LIKE CONCAT('%',?,'%') AND library.id = ? AND actions.status = 'inactive'";
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
        sql = "SELECT CURRVAL('actions_id_seq') ";
        List<Integer> currIndex = jdbcTemplate.query(sql, new CurrentValueRowMapper());
        if(currIndex.size() == 1){
            return currIndex.get(0);
        }
        else return -1;
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
    public void editAction(Action action) {
        String sql = "UPDATE actions SET title=?,description=?,idcompound=?,idtestscenario=?,status=?::action_status " +
                "WHERE id = ?";
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
        String sql = "UPDATE actions SET status = ?::action_status WHERE id = ?";
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
