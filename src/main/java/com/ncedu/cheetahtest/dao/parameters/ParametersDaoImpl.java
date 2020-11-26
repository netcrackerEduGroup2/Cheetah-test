package com.ncedu.cheetahtest.dao.parameters;


import com.ncedu.cheetahtest.entity.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ParametersDaoImpl implements ParametersDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ParametersDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Parameter findById(int id) {
        String sql = "SELECT id, id_data_set, type, value " +
                "FROM parameters WHERE id = ?";
        List<Parameter> parameters = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new ParametersRowMapper()
        );
        if (parameters.size() == 1) {
            return parameters.get(0);
        } else return null;
    }

    @Override
    public List<Parameter> findByTypeLike(String type, int idDataSet, int limit, int offset) {
        String sql = "SELECT id, id_data_set, type, value " +
                "FROM parameters WHERE type LIKE CONCAT('%',?,'%') AND id_data_set=? " +
                "ORDER BY type limit ? offset ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, type);
                    preparedStatement.setInt(2, idDataSet);
                    preparedStatement.setInt(3, limit);
                    preparedStatement.setInt(4, offset);
                },
                new ParametersRowMapper()
        );
    }

    @Override
    public Parameter createParameter(Parameter parameter) {
        String sql = "INSERT INTO parameters(id_data_set, type, value) VALUES (?,?,?)";
        jdbcTemplate.update(
                sql,
                parameter.getIdDataSet(),
                parameter.getType(),
                parameter.getValue()
        );
        return this.findByTypeAndIdDataSet(parameter.getType(), parameter.getIdDataSet());
    }

    @Override
    public Parameter editParameter(Parameter parameter, int id) {
        String sql = "UPDATE parameters SET id_data_set = ?, type = ?, value = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(
                sql,
                parameter.getIdDataSet(),
                parameter.getType(),
                parameter.getValue(),
                id
        );
        return findByTypeAndIdDataSet(parameter.getType(), parameter.getIdDataSet());
    }

    @Override
    public void deleteParameter(int id) {
        String sql = "DELETE FROM parameters WHERE id = ?";
        jdbcTemplate.update(sql, id);

    }

    @Override
    public int getTotalElements(int idDataSet) {
        String sql = "SELECT count(*) FROM parameters WHERE id_data_set= ?";
        List<Integer> count = jdbcTemplate.query(sql,
                preparedStatement -> preparedStatement.setInt(1,idDataSet),
                new CountParametersRowMapper());
        if (count.size() == 1) return count.get(0);
        else return 0;
    }

    @Override
    public Parameter findByTypeAndIdDataSet(String type, int idDataSet) {
        String sql = "SELECT id, id_data_set, type, value  FROM parameters " +
                "WHERE type = ? AND id_data_set = ?";
        List<Parameter> parameters = jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, type);
                    preparedStatement.setInt(2, idDataSet);
                },
                new ParametersRowMapper()
        );
        if (parameters.size() == 1) {
            return parameters.get(0);
        } else return null;
    }
}
