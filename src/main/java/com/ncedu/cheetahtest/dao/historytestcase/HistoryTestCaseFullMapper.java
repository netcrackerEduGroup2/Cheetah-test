package com.ncedu.cheetahtest.dao.historytestcase;

import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCaseFull;
import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryTestCaseFullMapper implements RowMapper<HistoryTestCaseFull> {
    public static final String ID = "id";
    public static final String RESULT = "result";
    public static final String DATA_COMPLETED = "date_completed";
    public static final String IDTESTCASE= "id_test_case";

    @Override
    public HistoryTestCaseFull mapRow(ResultSet resultSet, int i) throws SQLException {
        HistoryTestCaseFull historyTestCaseFull = new HistoryTestCaseFull();
        historyTestCaseFull.setId(resultSet.getInt(ID));
        historyTestCaseFull.setResult(TestCaseResult.valueOf(resultSet.getString(RESULT)));
        historyTestCaseFull.setDataCompleted(resultSet.getDate(DATA_COMPLETED));
        historyTestCaseFull.setIdTestCase(resultSet.getInt(IDTESTCASE));
        return historyTestCaseFull;
    }
}
