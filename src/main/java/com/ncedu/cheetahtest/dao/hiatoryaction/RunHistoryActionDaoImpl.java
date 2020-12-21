package com.ncedu.cheetahtest.dao.hiatoryaction;


import com.ncedu.cheetahtest.entity.actionresult.ActionResultForInfoDto;
import com.ncedu.cheetahtest.entity.runaction.RunAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.ncedu.cheetahtest.dao.hiatoryaction.HistoryActionConstant.*;

@Repository
public class RunHistoryActionDaoImpl implements RunHistoryActionDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RunHistoryActionDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<RunAction> getRunActionByTestHistoryId(int testCaseHistoryId) {
        return jdbcTemplate.query(GET_RUN_ACRIONS_BY_ID_TEST_CASE_HISTORY,
                preparedStatement ->
                        preparedStatement.setInt(1, testCaseHistoryId),
                new RunActionMapper());
    }

    @Override
    public List<Integer> getAllTestAcseHistoryId() {
        return jdbcTemplate.queryForList(GET_ALL_ID_HISTORY_TEST_CASE, Integer.class);
    }

    @Override
    public List<ActionResultForInfoDto> getLastRunDetailsByHTCId(int idTestCase) {
        return jdbcTemplate.query(
                GET_LAST_RUN_DETAILS,
                preparedStatement -> preparedStatement.setInt(1, idTestCase),
                new ActionResultDtoRowMapper()
        );
    }

    @Override
    public List<ActionResultForInfoDto> getLastRunDetailsByHTCIdPaginated(int idTestCase, int limit, int offset) {
        return jdbcTemplate.query(
                GET_LAST_RUN_DETAILS_BY_HTC_PAGINATED,
                preparedStatement -> {
                    preparedStatement.setInt(1, idTestCase);
                    preparedStatement.setInt(2, limit);
                    preparedStatement.setInt(3, offset);
                },
                new ActionResultDtoRowMapper()
        );
    }

    @Override
    public int countLastRunDetailsByHTCIdPaginated(int idTestCase) {
        List<Integer> counts = jdbcTemplate.query(
                COUNT_LAST_RUN_DETAILS,
                preparedStatement -> preparedStatement.setInt(1, idTestCase),
                new CountHistoryActionByTestCaseRowMapper()
        );
        if (counts.size() == 1) {
            return counts.get(0);
        } else return 0;
    }
}
