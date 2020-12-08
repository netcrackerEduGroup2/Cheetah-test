package com.ncedu.cheetahtest.dao.historytestcase;


import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.util.List;

import static com.ncedu.cheetahtest.dao.historytestcase.HistoryTestCaseConstant.*;

@Repository
public class HistoryTestCaseDaoImpl implements HistoryTestCaseDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HistoryTestCaseDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addTestCase(String result, String dateCompleted, int testCaseId) {
        jdbcTemplate.update(ADD_HISTORY_TEST_CASE,
                result, dateCompleted, testCaseId);
    }

    @Override
    public void editTestCaseResultById(int testCaseId, String result) {
        jdbcTemplate.update(EDIT_HISTORY_TEST_CASE_RESULT,
                result, testCaseId);
    }

    @Override
    public List<HistoryTestCase> getPage(int size, int page) {
        return jdbcTemplate.query(HISTORY_TEST_CASE_FAILED_COMPLETED_PAGINATION,
                preparedStatement -> {
                    preparedStatement.setInt(1, size);
                    preparedStatement.setInt(2, (page - 1) * size);
                },
                new HistoryTestCaseMapper());
    }

    @Override
    public Integer getCountTestCaseFailedCompleted(){
        return jdbcTemplate.queryForObject(COUNT_TEST_CASE_FAILED_COMPLETED,
                Integer.class);
    }
}
