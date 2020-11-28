package com.ncedu.cheetahtest.dao.compscenario;

import com.ncedu.cheetahtest.dao.actscenario.ActScenarioRowMapper;
import com.ncedu.cheetahtest.dao.compound.CompoundRowMapper;
import com.ncedu.cheetahtest.entity.actscenario.ActScenario;
import com.ncedu.cheetahtest.entity.actscenario.ActStatus;
import com.ncedu.cheetahtest.entity.compound.Compound;
import com.ncedu.cheetahtest.entity.compscenario.CompScenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CompScenarioDaoImpl implements CompScenarioDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CompScenarioDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public CompScenario createCompScenario(CompScenario compScenario) {
        String sql = "INSERT INTO comp_scenario (compound_id, test_scenario_id, priority, comp_status) " +
                "VALUES (?,?,?,?::action_status)";
        jdbcTemplate.update(
                sql,
                compScenario.getIdCompound(),
                compScenario.getIdTestScenario(),
                compScenario.getPriority(),
                compScenario.getStatus().toString()
        );
        return findBySignature(compScenario);

    }

    @Override
    public CompScenario editCompScenario(CompScenario compScenario, int id) {
        String sql = "UPDATE comp_scenario SET compound_id = ?,test_scenario_id = ? , priority = ?, comp_status = ?::action_status " +
                "WHERE  id = ?";
        jdbcTemplate.update(
                sql,
                compScenario.getIdCompound(),
                compScenario.getIdTestScenario(),
                compScenario.getPriority(),
                compScenario.getStatus().toString(),
                id
        );
        return findById(id);
    }

    @Override
    public void deleteCompScenario(int id) {
        String sql = "DELETE FROM comp_scenario WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<CompScenario> findByTitleLike(String title, int limit, int offset) {
        String sql = "SELECT cs.id, cs.compound_id, cs.test_scenario_id, cs.priority, cs.comp_status, c.title " +
                "FROM comp_scenario cs INNER JOIN compound c on c.id = cs.compound_id " +
                "WHERE c.title LIKE CONCAT('%',?,'%') ORDER BY c.title LIMIT ? OFFSET ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, limit);
                    preparedStatement.setInt(3, offset);
                },
                new CompScenarioRowMapper()
        );
    }

    @Override
    public List<CompScenario> findByTitleInTestScenario(String title, int idTestScenario, int limit, int offset) {
        String sql = "SELECT cs.id, cs.compound_id, cs.test_scenario_id, cs.priority, cs.comp_status , c.title " +
                "FROM comp_scenario cs INNER JOIN compound c on c.id = cs.compound_id " +
                "WHERE c.title LIKE CONCAT('%',?,'%') AND cs.test_scenario_id = ? ORDER BY cs.priority LIMIT ? OFFSET ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, idTestScenario);
                    preparedStatement.setInt(3, limit);
                    preparedStatement.setInt(4, offset);
                },
                new CompScenarioRowMapper()
        );
    }

    @Override
    public List<CompScenario> findAllByTitleLike(String title) {
        String sql = "SELECT cs.id, cs.compound_id, cs.test_scenario_id, cs.priority, cs.comp_status , c.title " +
                "FROM comp_scenario cs INNER JOIN compound c on c.id = cs.compound_id " +
                "WHERE c.title LIKE CONCAT('%',?,'%') ORDER BY c.title ";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, title),
                new CompScenarioRowMapper()
        );
    }

    @Override
    public List<CompScenario> findAllByTitleInTestScenario(String title, int idTestScenario) {
        String sql = "SELECT cs.id, cs.compound_id, cs.test_scenario_id, cs.priority, cs.comp_status, c.title " +
                "FROM comp_scenario cs INNER JOIN compound c on c.id = cs.compound_id " +
                "WHERE c.title LIKE CONCAT('%',?,'%') AND cs.test_scenario_id = ? ORDER BY cs.priority ";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, idTestScenario);
                },
                new CompScenarioRowMapper()
        );
    }

    @Override
    public int totalFindByTitleLike(String title) {
        String sql = "SELECT COUNT(*) FROM comp_scenario cs INNER JOIN compound c on c.id = cs.compound_id " +
                "WHERE c.title LIKE CONCAT('%',?,'%') ";
        List<Integer> counts = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, title),
                new CountCompScenarioRowMapper()
        );
        if (counts.size() == 1) {
            return counts.size();
        } else return 0;
    }

    @Override
    public int totalFindByTitleInTestScenario(String title, int idTestScenario) {
        String sql = "SELECT COUNT(*) FROM comp_scenario cs INNER JOIN compound c on c.id = cs.compound_id " +
                "WHERE c.title LIKE CONCAT('%',?,'%') AND cs.test_scenario_id = ? ";
        List<Integer> counts = jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, idTestScenario);
                },
                new CountCompScenarioRowMapper()
        );
        if (counts.size() == 1) {
            return counts.size();
        } else return 0;
    }

    @Override
    public void setStatusForAllByIdTestScenario(ActStatus actStatus, int idTestScenario) {
        String sql = "UPDATE comp_scenario SET comp_status = ?::action_status WHERE test_scenario_id = ?";
        jdbcTemplate.update(
                sql,
                actStatus.toString(),
                idTestScenario
        );

    }

    @Override
    public List<ActScenario> getAllActionScenarioInComp(int id) {
        String sql = "SELECT s.id, s.action_id, s.test_scenario_id, s.priority, s.action_status, s.param_id ,a.title,a.type " +
                "FROM act_scenario s INNER JOIN action a on a.id = s.action_id INNER JOIN comp_act_prior cap on a.id = cap.action_id " +
                "INNER JOIN compound c on cap.comp_id = c.id INNER JOIN comp_scenario cs on c.id = cs.compound_id " +
                "WHERE cs.id = ? ORDER BY cap.priority";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new ActScenarioRowMapper()
        );
    }

    @Override
    public CompScenario findById(int id) {
        String sql = "SELECT cs.id, cs.compound_id, cs.test_scenario_id, cs.priority, cs.comp_status, c.title " +
                "FROM comp_scenario cs INNER JOIN compound c on c.id = cs.compound_id " +
                "WHERE cs.id = ?";
        List<CompScenario> compScenarios = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new CompScenarioRowMapper()
        );
        if (compScenarios.size() == 1) {
            return compScenarios.get(0);
        } else {
            return null;
        }
    }

    @Override
    public CompScenario findBySignature(CompScenario compScenario) {
        String sql = "SELECT cs.id, cs.compound_id, cs.test_scenario_id, cs.priority, cs.comp_status, c.title " +
                "FROM comp_scenario cs INNER JOIN compound c on c.id = cs.compound_id " +
                "WHERE cs.compound_id = ? AND cs.test_scenario_id  =? AND cs.priority = ? AND cs.comp_status = ?::action_status ";
        List<CompScenario> compScenarios = jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setInt(1, compScenario.getIdCompound());
                    preparedStatement.setInt(2, compScenario.getIdTestScenario());
                    preparedStatement.setInt(3, compScenario.getPriority());
                    preparedStatement.setString(4, compScenario.getStatus().toString());
                },
                new CompScenarioRowMapper()
        );
        if (compScenarios.size() == 1) {
            return compScenarios.get(0);
        } else return null;
    }

    @Override
    public void deleteAllByIdTestScenario(int idTestScenario) {
        String sql = "DELETE FROM comp_scenario WHERE test_scenario_id = ?";
        jdbcTemplate.update(
                sql,
                idTestScenario);

    }

}
