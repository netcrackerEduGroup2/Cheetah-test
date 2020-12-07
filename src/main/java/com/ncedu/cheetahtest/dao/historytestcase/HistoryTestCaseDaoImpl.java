package com.ncedu.cheetahtest.dao.historytestcase;


import com.ncedu.cheetahtest.entity.history.HistoryTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.ncedu.cheetahtest.dao.historytestcase.HistoryTestCaseConstant.ADD_HISTORY_TEST_CASE;
import static com.ncedu.cheetahtest.dao.historytestcase.HistoryTestCaseConstant.HISTORY_TEST_CASE_PAGINATION;

@Repository
public class HistoryTestCaseDaoImpl implements HistoryTestCaseDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HistoryTestCaseDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int addTestCase(String result, Date dateCompleted, int testCaseId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection
                            .prepareStatement(ADD_HISTORY_TEST_CASE);
                    ps.setString(1, result);
                    ps.setTimestamp(2, new Timestamp(dateCompleted.getTime()));
                    ps.setInt(3, testCaseId);
                    return ps;
                }, keyHolder);

        return (int) Objects.requireNonNull(keyHolder.getKey(), "Id is null");
    }

    @Override
    public List<HistoryTestCase> getPage(int size, int page) {
        return jdbcTemplate.query(HISTORY_TEST_CASE_PAGINATION,
                preparedStatement -> {
                    preparedStatement.setInt(1, size);
                    preparedStatement.setInt(2, (page - 1) * size);
                },
                new HistoryTestCaseMapper());
    }
}
