package com.ncedu.cheetahtest.dao.compound;

import com.ncedu.cheetahtest.dao.action.CurrentValueRowMapper;
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
    public List<Compound> selectAll() {
        String sql = "SELECT id, title, description, idtestscenario, status FROM compounds";
        return jdbcTemplate.query(
                sql,
                new CompoundRowMapper()
        );
    }

    @Override
    public List<Compound> selectActiveCompoundByTitle(int idLibrary, String title) {
        String sql = "SELECT compounds.id, compounds.title, compounds.description, compounds.idtestscenario, " +
                "compounds.status FROM compounds INNER JOIN lib_act_compound ON compounds.id = lib_act_compound.id_compound " +
                "INNER JOIN library ON lib_act_compound.id_library = library.id " +
                "WHERE compounds.title LIKE CONCAT('%', ?, '%') AND library.id = ? AND compounds.status = 'active'";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, idLibrary);
                },
                new CompoundRowMapper()
        );
    }

    @Override
    public List<Compound> selectInactiveCompoundByTitle(int idLibrary, String title) {
        String sql = "SELECT compounds.id, compounds.title, compounds.description, compounds.idtestscenario, " +
                "compounds.status FROM compounds INNER JOIN lib_act_compound ON compounds.id = lib_act_compound.id_compound " +
                "INNER JOIN library ON lib_act_compound.id_library = library.id " +
                "WHERE compounds.title LIKE CONCAT('%', ?, '%') AND library.id = ? AND compounds.status='inactive'";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, idLibrary);
                },
                new CompoundRowMapper()
        );
    }

    @Override
    public int createCompound(Compound compound) {
        String sql = "INSERT INTO compounds (title,description,idtestscenario,status) " +
                "VALUES (?,?,?,?::action_status);";
        jdbcTemplate.update(
                sql,
                compound.getTitle(),
                compound.getDescription(),
                compound.getIdTestScenario(),
                compound.getStatus()
        );
        sql = "SELECT CURRVAL('compounds_id_seq')";
        List<Integer> currIndex = jdbcTemplate.query(sql, new CurrentValueRowMapper());
        if(currIndex.size() == 1) {
            return currIndex.get(0);
        }
        else return -1;
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
    public List<Compound> findCompoundByIdTestScenario(int idTestScenario) {
        String sql = "SELECT id,title,description,idtestscenario,status " +
                    "FROM compounds " +
                    "WHERE idtestscenario = ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idTestScenario),
                new CompoundRowMapper()
        );
    }

    @Override
    public Compound editCompound(Compound compoundDTO) {
        String sql = "UPDATE compounds SET title = ?, description = ?, idtestscenario = ?, status = ?::compound_status " +
                "WHERE compounds.id = ?";
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, compoundDTO.getTitle());
                    preparedStatement.setString(2, compoundDTO.getDescription());
                    preparedStatement.setInt(3, compoundDTO.getIdTestScenario());
                    preparedStatement.setString(4, compoundDTO.getStatus());
                    preparedStatement.setInt(5, compoundDTO.getId());
                    return preparedStatement.execute();
                }
        );
        return compoundDTO;
    }

    @Override
    public Compound setTitle(String title, int id) {
        String sql = "UPDATE compounds SET title = ? WHERE id = ?";
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }
        );
        return this.findCompoundById(id);
    }

    @Override
    public Compound setDescription(String description, int id) {
        String sql = "UPDATE compounds SET description = ? WHERE id = ?";
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, description);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }
        );
        return this.findCompoundById(id);
    }

    @Override
    public Compound setTestScenarioId(String testScenarioId, int id) {
        String sql = "UPDATE compounds SET idtestscenario = ? WHERE id = ?";
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, testScenarioId);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }
        );
        return this.findCompoundById(id);
    }

    @Override
    public Compound setStatus(String status, int id) {
        String sql = "UPDATE compounds SET status = ? WHERE id = ?";
        jdbcTemplate.execute(
                sql,
                (PreparedStatementCallback<Boolean>) preparedStatement -> {
                    preparedStatement.setString(1, status);
                    preparedStatement.setInt(2, id);
                    return preparedStatement.execute();
                }
        );
        return this.findCompoundById(id);
    }

    @Override
    public void removeCompoundById(int id) {
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
