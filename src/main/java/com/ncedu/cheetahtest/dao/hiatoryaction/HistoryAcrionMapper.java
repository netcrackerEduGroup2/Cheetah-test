package com.ncedu.cheetahtest.dao.hiatoryaction;

import com.ncedu.cheetahtest.entity.historyacrion.HistoryAction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryAcrionMapper implements RowMapper<HistoryAction> {
    public static final String ID = "id";
    public static final String COMPOUND_ID = "compound_id";
    public static final String RESULT = "result";
    public static final String SCREENSHOT_URL = "screenshot_url";
    public static final String GENERAL_ORDER = "general_order";
    public static final String ID_HISTORY_TEST_CASE = "id_history_test_case";

    @Override
    public HistoryAction mapRow(ResultSet rs, int rowNum) throws SQLException {
        HistoryAction historyAction = new HistoryAction();

        historyAction.setId(rs.getInt(ID));
        historyAction.setCompoundId(rs.getInt(COMPOUND_ID));
        historyAction.setResult(rs.getString(RESULT));
        historyAction.setScreenshotURL(rs.getString(SCREENSHOT_URL));
        historyAction.setGeneralOrder(rs.getInt(GENERAL_ORDER));
        historyAction.setIdHistoryTestCase(rs.getInt(ID_HISTORY_TEST_CASE));

        return historyAction;
    }
}
