package com.ncedu.cheetahtest.dao.dataset;

import com.ncedu.cheetahtest.entity.dataset.DataSet;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSetRowMapper implements RowMapper<DataSet> {
    public final String ID = "id";
    public final String TITLE = "title";
    public final String DESCRIPTION = "description";
    public final String IDTESTCASE = "id_test_case";

    @Override
    public DataSet mapRow(ResultSet resultSet, int i) throws SQLException {
        DataSet dataSet = new DataSet();
        dataSet.setId(resultSet.getInt(ID));
        dataSet.setTitle(resultSet.getString(TITLE));
        dataSet.setDescription(resultSet.getString(DESCRIPTION));
        dataSet.setIdTestCase(resultSet.getInt(IDTESTCASE));
        return dataSet;
    }
}