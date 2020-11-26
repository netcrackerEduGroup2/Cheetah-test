package com.ncedu.cheetahtest.dao.parameters;

import com.ncedu.cheetahtest.entity.parameter.Parameter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParametersRowMapper implements RowMapper<Parameter> {
    public static String ID = "id";
    public static String IDDATASET = "id_data_set";
    public static String TYPE ="type";
    public static String VALUE = "value";

    @Override
    public Parameter mapRow(ResultSet resultSet, int i) throws SQLException {
        Parameter parameter = new Parameter();
        parameter.setId(resultSet.getInt(ID));
        parameter.setIdDataSet(resultSet.getInt(IDDATASET));
        parameter.setType(resultSet.getString(TYPE));
        parameter.setValue(resultSet.getString(VALUE));
        return parameter;
    }
}
