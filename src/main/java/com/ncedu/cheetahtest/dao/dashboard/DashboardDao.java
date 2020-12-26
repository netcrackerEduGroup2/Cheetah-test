package com.ncedu.cheetahtest.dao.dashboard;

import com.ncedu.cheetahtest.entity.dashboard.PlannedTestCaseDTO;
import com.ncedu.cheetahtest.entity.dashboard.ProjectsSuppListDTO;
import com.ncedu.cheetahtest.entity.dashboard.UserActivityDTO;
import com.ncedu.cheetahtest.entity.dashboard.UserProjectsDTO;

import java.util.List;

public interface DashboardDao {

    List<UserActivityDTO> getActiveUsersForAdminPerDays(String days);

    List<UserActivityDTO> getActiveUsersForManagerPerDays(String days);

    int getCountActiveUserByRole(String role);

    int getProjectActivitiesPerDayOnWeek(String from, String to);

    Integer getCountAllProject();

    Integer getCountWeekProject(String oneWeekIntervalHours);

    List<UserProjectsDTO> getProjectsForUser(int id);

    int getCountArchiveProjects();

    int getCountLastTimeCreatedProject(String days);

    List<PlannedTestCaseDTO> getPlannedTestCasesForManager();

    List<PlannedTestCaseDTO> getPlannedTestCasesForEngineer(int id);

    List<ProjectsSuppListDTO> getSuppProjects();

    int getCountTestedCasesByProject(int projectId, String testCaseStatus);

    int getCountPlannedCasesByProject(int projectId);
}
