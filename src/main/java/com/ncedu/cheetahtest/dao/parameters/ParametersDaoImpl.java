package com.ncedu.cheetahtest.dao.parameters;


import com.ncedu.cheetahtest.entity.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.ncedu.cheetahtest.dao.parameters.ParametersConsts.*;

@Repository
public class ParametersDaoImpl implements ParametersDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ParametersDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Parameter findById(int id) {
        List<Parameter> parameters = jdbcTemplate.query(
                FIND_BY_ID,
                preparedStatement -> preparedStatement.setInt(1, id),
                new ParametersRowMapper()
        );
        if (parameters.size() == 1) {
            return parameters.get(0);
        } else return null;
    }

    @Override
    public List<Parameter> findByTypeLike(String type, int idDataSet, int limit, int offset) {
        return jdbcTemplate.query(
                FIND_BY_TITLE_LIKE,
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
    public List<Parameter> findAllByType(String type, int limit, int offset) {

        return jdbcTemplate.query(
                FIND_ALL_BY_TYPE,
                preparedStatement -> {
                    preparedStatement.setString(1,type);
                    preparedStatement.setInt(2,limit);
                    preparedStatement.setInt(3,offset);
                },
                new ParametersRowMapper()
        );

    }

    @Override
    public List<Parameter> findAllByIdDataSet(int idDataSet) {

        return jdbcTemplate.query(
                FIND_ALL_BY_ID_DATA_SET,
                preparedStatement -> preparedStatement.setInt(1,idDataSet),
                new ParametersRowMapper()
        );

    }

    @Override
    public List<Parameter> findAllByIdTestCase(int idTestCase) {
        return jdbcTemplate.query(
                FIND_ALL_BY_ID_TEST_CASE,
                preparedStatement -> preparedStatement.setInt(1,idTestCase),
                new ParametersRowMapper()
        );
    }

    @Override
    public Parameter createParameter(Parameter parameter) {
        jdbcTemplate.update(
                CREATE_PARAMETERS,
                parameter.getIdDataSet(),
                parameter.getType(),
                parameter.getValue()
        );
        return this.findByTypeAndIdDataSet(parameter.getType(), parameter.getIdDataSet());
    }

    @Override
    public Parameter editParameter(Parameter parameter, int id) {
        jdbcTemplate.update(
                EDIT_PARAMETER,
                parameter.getIdDataSet(),
                parameter.getType(),
                parameter.getValue(),
                id
        );
        return findByTypeAndIdDataSet(parameter.getType(), parameter.getIdDataSet());
    }

    @Override
    public void deleteParameter(int id) {
        jdbcTemplate.update(DELETE_PARAMETER, id);

    }

    @Override
    public int getTotalElements(int idDataSet, String type) {
        List<Integer> count = jdbcTemplate.query(
                GET_TOTAL_ELEMENTS,
                preparedStatement -> {
                    preparedStatement.setInt(1, idDataSet);
                    preparedStatement.setString(2, type);
                },
                new CountParametersRowMapper());
        if (count.size() == 1) return count.get(0);
        else return 0;
    }

    @Override
    public int getTotalAllElements(String  type) {
        List<Integer> count = jdbcTemplate.query(
                GET_TOTAL_ALL_ELEMENTS,
                preparedStatement -> preparedStatement.setString(1, type),
                new CountParametersRowMapper());
        if (count.size() == 1) return count.get(0);
        else return 0;
    }

    @Override
    public Parameter findByTypeAndIdDataSet(String type, int idDataSet) {
        List<Parameter> parameters = jdbcTemplate.query(
                FIND_BY_TITLE_AND_ID_DATA_SET,
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

    @Override
    public void deleteByIdDataSet(int idDataSet) {
        jdbcTemplate.update(
                DELETE_BY_ID_DATA_SET,
                idDataSet
        );
    }
}
