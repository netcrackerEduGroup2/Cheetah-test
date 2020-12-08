package com.ncedu.cheetahtest.dao.hiatoryaction;


import com.ncedu.cheetahtest.entity.runaction.RunAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.ncedu.cheetahtest.dao.hiatoryaction.HistoryActionConstant.GET_ALL_ID_HISTORY_TEST_CASE;
import static com.ncedu.cheetahtest.dao.hiatoryaction.HistoryActionConstant.GET_RUN_ACRIONS_BY_ID_TEST_CASE_HISTORY;

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
                preparedStatement -> {
                    preparedStatement.setInt(1, testCaseHistoryId);
                },
                new RunActionMapper());
    }

    @Override
    public List<Integer> getAllTestAcseHistoryId() {
        return jdbcTemplate.queryForList(GET_ALL_ID_HISTORY_TEST_CASE, Integer.class);
    }
}
