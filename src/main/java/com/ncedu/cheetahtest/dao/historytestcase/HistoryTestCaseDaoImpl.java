package com.ncedu.cheetahtest.dao.historytestcase;

import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCase;
import com.ncedu.cheetahtest.entity.historytestcase.HistoryTestCaseFull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
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
            newId = (Integer) Objects.requireNonNull(holder.getKeys()).get("id");
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
    public List<HistoryTestCase> getPage(int id, int size, int page) {
        return jdbcTemplate.query(HISTORY_TEST_CASE_FAILED_COMPLETED_PAGINATION,
                preparedStatement -> {
                    preparedStatement.setInt(1, id);
                    preparedStatement.setInt(2, size);
                    preparedStatement.setInt(3, (page - 1) * size);
                },
                new HistoryTestCaseMapper());
    }

    @Override
    public Integer getCountTestCaseFailedCompleted() {
        return jdbcTemplate.queryForObject(COUNT_TEST_CASE_FAILED_COMPLETED,
                Integer.class);
    }

    @Override
    public HistoryTestCaseFull getById(int id) {
        List<HistoryTestCaseFull> historyTestCases = jdbcTemplate.query(
                GET_BY_ID,
                preparedStatement -> preparedStatement.setInt(1, id),
                new HistoryTestCaseFullMapper()
        );
        if (historyTestCases.size() == 1) return historyTestCases.get(0);
        else return null;
    }
}
