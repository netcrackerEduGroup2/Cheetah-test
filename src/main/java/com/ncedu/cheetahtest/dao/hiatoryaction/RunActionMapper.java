package com.ncedu.cheetahtest.dao.hiatoryaction;

import com.ncedu.cheetahtest.entity.runaction.RunAction;
import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RunActionMapper implements RowMapper<RunAction> {
    public static final String ID ="ar_id";
    public static final String COMPOUND_ID = "ar_compound_id";
    public static final String RESULT = "ar_result";
    public static final String SCREENSHOT_URL = "ar_screenshot_url";
    public static final String ACTION_TYPE = "a_type";
    public static final String ELEMENT = "ar_action_element";
    public static final String ARGUMENT = "ar_argument";

    @Override
    public RunAction mapRow(ResultSet rs, int rowNum) throws SQLException {
        RunAction runAction = new RunAction();

        runAction.setId(rs.getInt(ID));
        runAction.setCompoundId(rs.getInt(COMPOUND_ID));
        runAction.setResult(TestCaseResult.valueOf(rs.getString(RESULT)));
        runAction.setScreenshotURL(rs.getString(SCREENSHOT_URL));
        runAction.setActionType(rs.getString(ACTION_TYPE));
        runAction.setElement(rs.getString(ELEMENT));
        runAction.setArgument(rs.getString(ARGUMENT));

        return runAction;
    }
}
