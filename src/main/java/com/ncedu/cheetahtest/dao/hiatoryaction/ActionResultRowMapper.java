package com.ncedu.cheetahtest.dao.hiatoryaction;

import com.ncedu.cheetahtest.entity.selenium.ActionResult;
import com.ncedu.cheetahtest.entity.selenium.ActionResultStatus;
import com.ncedu.cheetahtest.entity.selenium.SeleniumAction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActionResultRowMapper implements RowMapper<ActionResult> {
    public static final String ID = "ar_id";
    public static final String ACTIONTYPE = "a_type";
    public static final String ELEMENT = "ar_element";
    public static final String ARGUMENT = "ar_argument";
    public static final String DESCRIPTION = "ar_result";
    public static final String SCREENSHOT = "ar_screenshot";

    @Override
    public ActionResult mapRow(ResultSet resultSet, int i) throws SQLException {
        SeleniumAction seleniumAction = new SeleniumAction();
        seleniumAction.setActionType(resultSet.getString(ACTIONTYPE));
        seleniumAction.setElement(resultSet.getString(ELEMENT));
        seleniumAction.setArgument(resultSet.getString(ARGUMENT));

        ActionResult actionResult = new ActionResult();
        actionResult.setAction(seleniumAction);
        String des = resultSet.getString(DESCRIPTION);
        actionResult.setResultDescription(resultSet.getString(DESCRIPTION));
        if (des.equals("Action was successfully executed.")) {
            actionResult.setStatus(ActionResultStatus.SUCCESS);
        } else {
            actionResult.setStatus(ActionResultStatus.FAIL);
        }
        actionResult.setScreenshotUrl(resultSet.getString(SCREENSHOT));
        return actionResult;

    }
}
