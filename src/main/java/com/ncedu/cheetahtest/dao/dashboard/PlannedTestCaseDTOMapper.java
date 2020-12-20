package com.ncedu.cheetahtest.dao.dashboard;

import com.ncedu.cheetahtest.entity.dashboard.PlannedTestCaseDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlannedTestCaseDTOMapper implements RowMapper<PlannedTestCaseDTO> {
    private static final String ID_PROJECT = "id_project";
    private static final String TITLE_PROJECT = "title_project";
    private static final String ID_TESTCASE = "id_testcase";
    private static final String TITLE_TESTCASE = "title_testcase";
    private static final String CRON_DATE = "cron_date";

    @Override
    public PlannedTestCaseDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        PlannedTestCaseDTO plannedTestCaseDTO = new PlannedTestCaseDTO();

        plannedTestCaseDTO.setIdProject(resultSet.getInt(ID_PROJECT));
        plannedTestCaseDTO.setTitleProject(resultSet.getString(TITLE_PROJECT));
        plannedTestCaseDTO.setIdTestCase(resultSet.getInt(ID_TESTCASE));
        plannedTestCaseDTO.setTitleTestCase(resultSet.getString(TITLE_TESTCASE));
        plannedTestCaseDTO.setCronDate(resultSet.getString(CRON_DATE));

        return plannedTestCaseDTO;
    }
}
