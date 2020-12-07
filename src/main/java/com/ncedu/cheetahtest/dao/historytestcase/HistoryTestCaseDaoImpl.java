package com.ncedu.cheetahtest.dao.historytestcase;

import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.ncedu.cheetahtest.dao.historytestcase.HistoryTestCaseConstant.*;

@Repository
public class HistoryTestCaseDaoImpl implements HistoryTestCaseDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HistoryTestCaseDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

//    @Override
//    public int addTestCase(String result, Date dateCompleted, int testCaseId) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        jdbcTemplate.update(
//                connection -> {
//                    PreparedStatement ps = connection
//                            .prepareStatement(ADD_HISTORY_TEST_CASE);
//                    ps.setString(1, result);
//                    ps.setTimestamp(2, new Timestamp(dateCompleted.getTime()));
//                    ps.setInt(3, testCaseId);
//                    return ps;
//                }, keyHolder);
//
//
//
//        return (int) Objects.requireNonNull(keyHolder.getKey(), "Id is null");
//    }

    @Override
    public int addTestCase(String result, Date dateCompleted, int testCaseId) {

        final PreparedStatementCreator psc = connection -> {
            final PreparedStatement ps = connection.prepareStatement(ADD_HISTORY_TEST_CASE,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, result);
            ps.setTimestamp(2, new Timestamp(dateCompleted.getTime()));
            ps.setInt(3, testCaseId);
            return ps;
        };
        final KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(psc, holder);

        Integer newId;
        if (Objects.requireNonNull(holder.getKeys()).size() > 1) {
            newId = (Integer) holder.getKeys().get("id");
        } else {
            newId = Objects.requireNonNull(holder.getKey()).intValue();
        }
        return newId;
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
    public Integer getCountTestCaseFailedCompleted() {
        return jdbcTemplate.queryForObject(COUNT_TEST_CASE_FAILED_COMPLETED,
                Integer.class);
    }
}
