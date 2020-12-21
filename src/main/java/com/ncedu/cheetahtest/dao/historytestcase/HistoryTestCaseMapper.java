package com.ncedu.cheetahtest.dao.historytestcase;

import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCase;
import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryTestCaseMapper implements RowMapper<HistoryTestCase> {

    public static final String ID = "h_id";
    public static final String RESULT = "h_result";
    public static final String DATA_COMPLETED = "h_date_completed";

    @Override
    public HistoryTestCase mapRow(ResultSet resultSet, int i) throws SQLException {
        HistoryTestCase historyTestCase = new HistoryTestCase();

        historyTestCase.setId(resultSet.getInt(ID));
        historyTestCase.setResult(TestCaseResult.valueOf(resultSet.getString(RESULT)));
        historyTestCase.setDataCompleted(resultSet.getTimestamp(DATA_COMPLETED));

        return historyTestCase;
    }
}
