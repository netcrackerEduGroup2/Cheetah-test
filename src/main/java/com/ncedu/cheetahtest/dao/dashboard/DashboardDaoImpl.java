package com.ncedu.cheetahtest.dao.dashboard;

import com.ncedu.cheetahtest.dao.genericdao.CountRowMapper;
import com.ncedu.cheetahtest.entity.dashboard.PlannedTestCaseDTO;
import com.ncedu.cheetahtest.entity.dashboard.SuppProjectsDTO;
import com.ncedu.cheetahtest.entity.dashboard.UserActivityDTO;
import com.ncedu.cheetahtest.entity.dashboard.UserProjectsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ncedu.cheetahtest.dao.dashboard.DashboardConsts.*;


@Repository
@RequiredArgsConstructor
public class DashboardDaoImpl implements DashboardDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<UserActivityDTO> getActiveUsersForAdminPerDays(String days) {
        return jdbcTemplate.query(
                GET_ALL_USER_ACTIVITY_SQL,
                preparedStatement -> preparedStatement.setString(1, "'" + days + " days'"),
                new UserActivityMapper()
        );
    }

    @Override
    public List<UserActivityDTO> getActiveUsersForManagerPerDays(String days) {
        return jdbcTemplate.query(
                GET_USER_ACTIVITY_FOR_MANAGER_SQL,
                preparedStatement -> preparedStatement.setString(1, "'" + days + " days'"),
                new UserActivityMapper()
        );
    }

    @Override
    public int getCountActiveUserByRole(String role) {
        List<Integer> count = jdbcTemplate.query(COUNT_ACTIVE_USER_BY_ROLE_SQL,
                preparedStatement -> preparedStatement.setString(1, role),
                new CountRowMapper());
        if (count.size() == 1) return count.get(0);
        else return 0;
    }

    @Override
    public int getProjectActivitiesPerDayOnWeek(String from, String to) {
        List<Integer> count = jdbcTemplate.query(
                COUNT_PROJECTS_CREATED_PER_DAY_ON_WEEK,
                preparedStatement -> {
                    preparedStatement.setString(1, "'" + from + " hours'");
                    preparedStatement.setString(2, "'" + to + " hours'");
                },
                new CountRowMapper());
        if (count.size() == 1) return count.get(0);
        else return 0;
    }

    @Override
    public Integer getCountAllProject() {
        List<Integer> count = jdbcTemplate.query(COUNT_ALL_PROJECT_SQL, new CountRowMapper());
        if (count.size() == 1) return count.get(0);
        else return 0;
    }

    @Override
    public Integer getCountWeekProject(String oneWeekIntervalHours) {
        List<Integer> count = jdbcTemplate.query(COUNT_PROJECT_PER_WEEK_SQL,
                preparedStatement -> preparedStatement.setString(1, "'" + oneWeekIntervalHours + " hours'"),
                new CountRowMapper());
        if (count.size() == 1) return count.get(0);
        else return 0;
    }

    @Override
    public List<UserProjectsDTO> getProjectsForUser(int id) {
        return jdbcTemplate.query(
                GET_PROJECTS_FOR_USER_SQL,
                preparedStatement -> preparedStatement.setInt(1, id),
                new UserProjectsDTOMapper()
        );
    }

    @Override
    public int getCountArchiveProjects() {
        List<Integer> count = jdbcTemplate.query(COUNT_ARCHIVED_PROJECTS_SQL, new CountRowMapper());
        if (count.size() == 1) return count.get(0);
        else return 0;
    }

    @Override
    public int getCountLastTimeCreatedProject(String days) {
        List<Integer> count = jdbcTemplate.query(COUNT_LAST_TIME_CREATED_PROJECTS_,
                preparedStatement -> preparedStatement.setString(1, "'" + days + " days'"),
                new CountRowMapper());
        if (count.size() == 1) return count.get(0);
        else return 0;
    }

    @Override
    public List<PlannedTestCaseDTO> getPlannedTestCasesForManager() {
        return jdbcTemplate.query(
                GET_PLANNED_TESTCASES_FOR_MANAGER,
                new PlannedTestCaseDTOMapper()
        );
    }

    @Override
    public List<PlannedTestCaseDTO> getPlannedTestCasesForEngineer(int id) {
        return jdbcTemplate.query(
                GET_PLANNED_TESTCASES_FOR_ENGINEER,
                preparedStatement -> preparedStatement.setInt(1, id),
                new PlannedTestCaseDTOMapper()
        );
    }

    @Override
    public List<SuppProjectsDTO> getSuppProjects() {
        return jdbcTemplate.query(
                GET_SUPP_LIST_OF_PROJECTS,
                new SuppProjectsDTOMapper()
        );
    }

    @Override
    public int getCountTestedCasesByProject(int projectId, String testCaseStatus) {
        List<Integer> count = jdbcTemplate.query(COUNT_ENDED_TEST_CASES,
                preparedStatement -> {
                    preparedStatement.setInt(1, projectId);
                    preparedStatement.setString(2, testCaseStatus);
                },
                new CountRowMapper());
        if (count.size() == 1) return count.get(0);
        else return 0;
    }

    @Override
    public int getCountPlannedCasesByProject(int projectId) {
        List<Integer> count = jdbcTemplate.query(COUNT_PLANNED_TEST_CASES,
                preparedStatement -> preparedStatement.setInt(1, projectId),
                new CountRowMapper());
        if (count.size() == 1) return count.get(0);
        else return 0;
    }
}
