package com.ncedu.cheetahtest.dao.hiatoryaction;

import com.ncedu.cheetahtest.entity.actionresult.ActionResultForInfoDto;
import com.ncedu.cheetahtest.entity.selenium.ActionResultStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActionResultDtoRowMapper implements RowMapper<ActionResultForInfoDto> {
    public static final String ID = "ar_id";
    public static final String ACTIONTYPE = "a_type";
    public static final String ELEMENT = "ar_element";
    public static final String ARGUMENT = "ar_argument";
    public static final String DESCRIPTION = "ar_result";
    public static final String SCREENSHOT = "ar_screenshot";

    @Override
    public ActionResultForInfoDto mapRow(ResultSet resultSet, int i) throws SQLException {
        ActionResultForInfoDto actionResult = new ActionResultForInfoDto();
        actionResult.setId(resultSet.getInt(ID));
        actionResult.setActionType(resultSet.getString(ACTIONTYPE));
        String description = resultSet.getString(DESCRIPTION);
        actionResult.setResultDescription(resultSet.getString(DESCRIPTION));
        if (description.equals("Action was successfully executed.")) {
            actionResult.setStatus(ActionResultStatus.SUCCESS);
        } else {
            actionResult.setStatus(ActionResultStatus.FAIL);
        }
        actionResult.setScreenshotUrl(resultSet.getString(SCREENSHOT));
        actionResult.setElement(resultSet.getString(ELEMENT));
        actionResult.setArgument(resultSet.getString(ARGUMENT));

        return actionResult;
    }
}
