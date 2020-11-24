package com.ncedu.cheetahtest.dao.compound;

import com.ncedu.cheetahtest.dao.action.CurrentValueRowMapper;
import com.ncedu.cheetahtest.entity.compound.Compound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.ncedu.cheetahtest.dao.compound.CompoundConsts.*;

@Repository
public class CompoundDaoImpl implements CompoundDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CompoundDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Compound> selectAll() {
        return jdbcTemplate.query(
                SELECT_ALL,
                new CompoundRowMapper()
        );
    }

    @Override
    public List<Compound> selectActiveCompoundByTitle(int idLibrary, String title) {
        return jdbcTemplate.query(
                SELECT_ACTIVE_COMPOUND_BY_TITLE,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, idLibrary);
                },
                new CompoundRowMapper()
        );
    }

    @Override
    public List<Compound> selectInactiveCompoundByTitle(int idLibrary, String title) {
        return jdbcTemplate.query(
                SELECT_INACTIVE_COMPOUND_BY_TITLE,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, idLibrary);
                },
                new CompoundRowMapper()
        );
    }

    @Override
    public int createCompound(Compound compound) {
        String sql = CREATE_COMPOUND;
        jdbcTemplate.update(
                sql,
                compound.getTitle(),
                compound.getDescription(),
                compound.getIdTestScenario(),
                compound.getStatus()
        );
        sql = SELECT_CURRVAL_COMPOUNDS_ID;
        List<Integer> currIndex = jdbcTemplate.query(sql, new CurrentValueRowMapper());
        if(currIndex.size() == 1) {
            return currIndex.get(0);
        }
        else return -1;
    }

    @Override
    public Compound findCompoundById(int id) {
        List<Compound> compounds = jdbcTemplate.query(
                FIND_COMPOUND_BY_ID,
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
        return jdbcTemplate.query(
                FIND_COMPOUND_BY_ID_TESTSCENARIO,
                preparedStatement -> preparedStatement.setInt(1, idTestScenario),
                new CompoundRowMapper()
        );
    }

    @Override
    public Compound editCompound(Compound compoundDTO) {
        jdbcTemplate.update(
                EDIT_COMPOUND,
                 compoundDTO.getTitle(),
                 compoundDTO.getDescription(),
                 compoundDTO.getIdTestScenario(),
                 compoundDTO.getStatus(),
                 compoundDTO.getId()
        );
        return compoundDTO;
    }

    @Override
    public Compound setTitle(String title, int id) {
        jdbcTemplate.update(
                SET_TITLE,
                title,
                id
        );
        return this.findCompoundById(id);
    }

    @Override
    public Compound setDescription(String description, int id) {
        jdbcTemplate.update(
                SET_DESCRIPTION,
                description,
                id
        );
        return this.findCompoundById(id);
    }

    @Override
    public Compound setTestScenarioId(String testScenarioId, int id) {
        jdbcTemplate.update(
                SET_TESTSCENARIO_ID,
                testScenarioId,
                id
        );
        return this.findCompoundById(id);
    }

    @Override
    public Compound setStatus(String status, int id) {
        jdbcTemplate.update(
                SET_STATUS,
                status,
                id
        );
        return this.findCompoundById(id);
    }

    @Override
    public void removeCompoundById(int id) {
        jdbcTemplate.update(
                REMOVE_COMPOUND_BY_ID,
                id
        );

    }
}
