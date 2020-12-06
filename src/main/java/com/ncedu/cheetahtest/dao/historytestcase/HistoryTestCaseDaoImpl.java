package com.ncedu.cheetahtest.dao.historytestcase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import static com.ncedu.cheetahtest.dao.historytestcase.HistoryTestCaseConstant.ADD_HISTORY_TEST_CASE;

@Repository
public class HistoryTestCaseDaoImpl implements HistoryTestCaseDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HistoryTestCaseDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addTestCase(String result, String dateCompleted, int testCaseId) {
        jdbcTemplate.update(
                ADD_HISTORY_TEST_CASE,
                preparedStatement -> {
                    preparedStatement.setString(1, result);
                    preparedStatement.setString(2, dateCompleted);
                    preparedStatement.setInt(3, testCaseId);
                });
    }
}
