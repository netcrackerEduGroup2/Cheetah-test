package com.ncedu.cheetahtest.dao.historytestcase;

import com.ncedu.cheetahtest.entity.history.HistoryTestCase;
import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryTestCaseMapper implements RowMapper<HistoryTestCase> {

    public static final String ID = "id";
    public static final String RESULT = "result";
    public static final String DATA_COMPLETED = "date_completed";
    public static final String ID_TEST_CASE = "id_test_case";

    @Override
    public HistoryTestCase mapRow(ResultSet resultSet, int i) throws SQLException {
        HistoryTestCase historyTestCase = new HistoryTestCase();

        historyTestCase.setId(resultSet.getInt(ID));
        historyTestCase.setResult(TestCaseResult.valueOf(resultSet.getString(RESULT)));
        historyTestCase.setDataCompleted(resultSet.getTimestamp(DATA_COMPLETED));
        historyTestCase.setIdTestCase(resultSet.getInt(ID_TEST_CASE));

        return historyTestCase;
    }
}
