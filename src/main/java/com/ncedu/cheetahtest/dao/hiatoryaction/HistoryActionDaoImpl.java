package com.ncedu.cheetahtest.dao.hiatoryaction;

import com.ncedu.cheetahtest.entity.historyacrion.HistoryAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.util.List;

import static com.ncedu.cheetahtest.dao.hiatoryaction.HistoryActionConstant.ADD_HISTORY_ACTION;
import static com.ncedu.cheetahtest.dao.hiatoryaction.HistoryActionConstant.GET_HISTORY_ACTION_BY_TEST_HISTORY_ID;

@Repository
public class HistoryActionDaoImpl implements HistoryActionDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HistoryActionDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public void addAction(String result, String screenshotURL, int generalOrder,
                          int idHistoryTestCase, int compoundId) {
        jdbcTemplate.update(ADD_HISTORY_ACTION, result, screenshotURL,
                generalOrder, idHistoryTestCase, compoundId);

    }

    @Override
    public List<HistoryAction> getHistoryActionByTestHistoryId(int testCaseHistoryId) {
        return jdbcTemplate.query(GET_HISTORY_ACTION_BY_TEST_HISTORY_ID,
                preparedStatement -> {
                    preparedStatement.setInt(1, testCaseHistoryId);
                }, new HistoryAcrionMapper());
    }
}
