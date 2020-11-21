package com.ncedu.cheetahtest.dao.compound;

import com.ncedu.cheetahtest.entity.compound.Compound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
@Repository
public class CompoundDaoImpl implements CompoundDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CompoundDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createCompound(Compound compound) {
        String sql = "INSERT INTO compounds (title,description,idtestscenario,status) " +
                "VALUES (?,?,?,?::action_status);";
        jdbcTemplate.update(
                sql,
                compound.getTitle(),
                compound.getDescription(),
                compound.getIdTestScenario(),
                compound.getStatus()
        );

    }

    @Override
    public Compound findCompoundById(int id) {
        String sql = "SELECT id,title,description,idtestscenario,status " +
                "FROM compounds WHERE id = ?";
        List<Compound> compounds = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new CompoundRowMapper()
        );

        if (compounds.size() == 1) {
            return compounds.get(0);
        }
        return null;
    }

    @Override
    public Compound findCompoundByTitle(String title) {
        String sql = "SELECT id,title,description,idtestscenario,status " +
                "FROM compounds WHERE title = ?";
        List<Compound> compounds = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, title),
                new CompoundRowMapper()
        );

        if (compounds.size() == 1) {
            return compounds.get(0);
        }
        return null;
    }

    @Override
    public List<Compound> findCompoundByIdTestScenario(int idTestScenario) {
        String sql = "SELECT id,title,description,idtestscenario,status " +
                "FROM compounds WHERE idtestscenario = ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idTestScenario),
                new CompoundRowMapper()
        );
    }

    @Override
    public void setTitle(String title, int id) {
        String sql = "UPDATE compounds SET title = ? WHERE id = ?";
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
        String sql = "UPDATE compounds SET description = ? WHERE id = ?";
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
    public void setTestScenarioId(String testScenarioId, int id) {
        String sql = "UPDATE compounds SET idtestscenario = ? WHERE id = ?";
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
        String sql = "UPDATE compounds SET status = ? WHERE id = ?";
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
    public void removeCompound(int id) {
        String sql = "DELETE FROM compounds WHERE id = ?";
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setInt(1, id);
                    return preparedStatement.execute();
                }
        );

    }
}
