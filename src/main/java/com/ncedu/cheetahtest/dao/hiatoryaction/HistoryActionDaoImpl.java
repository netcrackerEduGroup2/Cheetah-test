package com.ncedu.cheetahtest.dao.hiatoryaction;

import com.ncedu.cheetahtest.entity.historyacrion.HistoryAction;
import com.ncedu.cheetahtest.entity.selenium.ActionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.ncedu.cheetahtest.dao.hiatoryaction.HistoryActionConstant.*;

@Repository
public class HistoryActionDaoImpl implements HistoryActionDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HistoryActionDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public void addAction(String result, String screenshotURL, int generalOrder,
                          int idHistoryTestCase, int compoundId, int idAction, String element, String argument) {

        jdbcTemplate.update(ADD_HISTORY_ACTION, result, screenshotURL,
                generalOrder, idHistoryTestCase, compoundId, element, argument, idAction);

    }

    @Override
    public void addAction(String result, String screenshotURL, int generalOrder,
                          int idHistoryTestCase, int idAction, String element,
                          String argument) {
        jdbcTemplate.update(ADD_HISTORY_ACTION_WITHOUT_COMPOUND, result, screenshotURL,
                generalOrder, idHistoryTestCase, element, argument, idAction);
    }

    @Override
    public List<HistoryAction> getHistoryActionByTestHistoryId(int testCaseHistoryId) {
        return jdbcTemplate.query(GET_HISTORY_ACTION_BY_TEST_HISTORY_ID,
                preparedStatement ->
                        preparedStatement.setInt(1, testCaseHistoryId),
                new HistoryAcrionMapper());
    }

    @Override
    public List<ActionResult> getHistoryActionsByTestCaseId(int idTestCase) {
        String sql = "SELECT ar.id as ar_id,a.type as a_type, ar.action_element as ar_element,ar.argument as ar_argument, " +
                "ar.result as ar_result, ar.screenshot_url as ar_screenshot " +
                "FROM action_result ar " +
                "INNER JOIN action a on a.id = ar.id_action " +
                "INNER JOIN history_test_case htc on ar.id_history_test_case = htc.id " +
                "WHERE htc.id = (SELECT id FROM history_test_case WHERE id_test_case=? ORDER BY date_completed DESC LIMIT 1)" +
                "ORDER BY ar.general_order";

        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idTestCase),
                new ActionResultRowMapper()
        );

    }
}
