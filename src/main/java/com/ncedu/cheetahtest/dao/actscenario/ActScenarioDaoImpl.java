package com.ncedu.cheetahtest.dao.actscenario;

import com.ncedu.cheetahtest.entity.actscenario.ActScenario;
import com.ncedu.cheetahtest.entity.actscenario.ActStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ActScenarioDaoImpl implements ActScenarioDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ActScenarioDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ActScenario createActScenario(ActScenario actScenario) {
        String sql = "INSERT INTO act_scenario (action_id, test_scenario_id, priority, action_status, param_id) " +
                "VALUES (?,?,?,?::acttion_status,?)";
        jdbcTemplate.update(
                sql,
                actScenario.getActionId(),
                actScenario.getTestScenarioId(),
                actScenario.getPriority(),
                actScenario.getActStatus(),
                actScenario.getParameterId());
        return findById(actScenario.getId());
    }

    @Override
    public ActScenario editActScenario(ActScenario actScenario, int id) {
        String sql = "UPDATE act_scenario SET action_id = ?, test_scenario_id = ?, priority = ?, action_status = ?::action_status,param_id = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(
                sql,
                actScenario.getActionId(),
                actScenario.getTestScenarioId(),
                actScenario.getPriority(),
                actScenario.getActStatus().toString(),
                actScenario.getParameterId(),
                id);
        return this.findById(actScenario.getId());
    }

    @Override
    public List<ActScenario> findByTitleLike(String title, int limit, int offset) {
        String sql = "SELECT s.id, s.action_id, s.test_scenario_id, s.priority, s.action_status, s.param_id, a.title,a.type,p.type,p.value " +
                "FROM act_scenario s INNER JOIN action a ON a.id = s.action_id " +
                "INNER JOIN parameters p ON s.param_id = p.id " +
                "WHERE a.title LIKE CONCAT('%',?,'%') ORDER BY s.priority LIMIT ? OFFSET ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, limit);
                    preparedStatement.setInt(3, offset);
                },
                new ActScenarioRowMapper()
        );
    }

    @Override
    public List<ActScenario> findAllByTitleLike(String title) {
        String sql = "SELECT s.id, s.action_id, s.test_scenario_id, s.priority, s.action_status, s.param_id, a.title,a.type,p.type,p.value " +
                "FROM act_scenario s INNER JOIN action a ON a.id = s.action_id " +
                "INNER JOIN parameters p ON s.param_id = p.id " +
                "WHERE a.title LIKE CONCAT('%',?,'%')";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, title),
                new ActScenarioRowMapper()
        );
    }

    @Override
    public List<ActScenario> findAllByTitleInTestScenario(String title, int idTestScenario) {
        String sql = "SELECT s.id, s.action_id, s.test_scenario_id, s.priority, s.action_status, s.param_id, a.title,a.type,p.type,p.value " +
                "FROM act_scenario s INNER JOIN action a ON a.id = s.action_id " +
                "INNER JOIN parameters p ON s.param_id = p.id " +
                "WHERE a.title LIKE CONCAT('%',?,'%') AND s.test_scenario_id = ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2,idTestScenario);
                },
                new ActScenarioRowMapper()
        );
    }

    @Override
    public List<ActScenario> findByTitleInTestScenario(String title, int idTestScenario,int limit , int offset) {
        String sql = "SELECT s.id, s.action_id, s.test_scenario_id, s.priority, s.action_status, s.param_id, a.title,a.type,p.type,p.value " +
                "FROM act_scenario s INNER JOIN action a ON a.id = s.action_id " +
                "INNER JOIN parameters p ON s.param_id = p.id " +
                "WHERE a.title LIKE CONCAT('%',?,'%') AND s.test_scenario_id = ? ORDER BY s.priority LIMIT ? OFFSET ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, idTestScenario);
                    preparedStatement.setInt(3, limit);
                    preparedStatement.setInt(4, offset);
                },
                new ActScenarioRowMapper()
        );
    }

    @Override
    public void deleteActScenario(int id) {
        String sql = "DELETE FROM act_scenario WHERE id =?";
        jdbcTemplate.update(sql, id);
    }


    @Override
    public ActScenario setStatus(ActStatus actStatus, int id) {
        String sql = "UPDATE act_scenario SET action_status = ?::action_status WHERE id = ?";
        jdbcTemplate.update(
                sql,
                actStatus.toString(),
                id
        );
        return this.findById(id);
    }

    @Override
    public ActScenario findById(int id) {
        String sql = "SELECT s.id, s.action_id, s.test_scenario_id, s.priority, s.action_status, s.param_id, a.title, a.type, p.type,p.value " +
                "FROM act_scenario s INNER JOIN action a on a.id = s.action_id " +
                "INNER JOIN parameters p on s.param_id = p.id " +
                "WHERE s.id = ?";
        List<ActScenario> actScenarios = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new ActScenarioRowMapper());
        if (actScenarios.size() == 1) {
            return actScenarios.get(0);
        } else {
            return null;
        }
    }

    @Override
    public int totalFindByTitleLike(String title) {
        String sql = "SELECT COUNT(*) " +
                "FROM act_scenario s INNER JOIN action a on a.id = s.action_id WHERE a.title LIKE ('%',?,'%')";
        List<Integer> counts = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, title),
                new CountActScenarioRowMapper());
        if (counts.size() == 1) {
            return counts.get(0);
        } else {
            return 0;
        }
    }

    @Override
    public int totalFindByTitleInTestScenario(String title,int idTestScenario) {
        String sql = "SELECT COUNT(*) FROM act_scenario s " +
                "INNER JOIN action a on a.id = s.action_id WHERE a.title LIKE ('%',?,'%') AND test_scenario_id = ?";
        List<Integer> counts = jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, idTestScenario);
                },
                new CountActScenarioRowMapper());
        if (counts.size() == 1) {
            return counts.get(0);
        } else {
            return 0;
        }
    }

    @Override
    public void setStatusForAllByIdTestScenario(ActStatus actStatus,int idTestScenario) {
        String sql = "UPDATE act_scenario SET action_status = ?::action_status WHERE test_scenario_id = ?";
        jdbcTemplate.update(sql,actStatus.toString(), idTestScenario);
    }

    @Override
    public void deleteAllByIdTestScenario(int idTestScenario) {
        String sql = "DELETE FROM act_scenario WHERE test_scenario_id = ?";
        jdbcTemplate.update(
                sql,
                idTestScenario);
    }

    @Override
    public void deleteAllByIdCompScenario(int idCompScenario) {
        String sql = "DELETE FROM act_scenario WHERE id = ANY(" +
                "SELECT s.id FROM act_scenario s INNER JOIN action a on a.id = s.action_id " +
                "INNER JOIN comp_act_prior cap on a.id = cap.action_id " +
                "INNER JOIN compound c on cap.comp_id = c.id " +
                "INNER JOIN comp_scenario cs on c.id = cs.compound_id " +
                "WHERE cs.id = ?)";
        jdbcTemplate.update(sql,idCompScenario);
    }
}
