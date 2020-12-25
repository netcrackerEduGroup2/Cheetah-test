package com.ncedu.cheetahtest.service.dashboard;

import com.ncedu.cheetahtest.entity.dashboard.*;

import java.util.List;

public interface DashboardService {

    List<UserActivityDTO> getActiveUsersForAdminPerDay();

    List<UserActivityDTO> getActiveUsersForManagerPerDay();

    List<Integer> getUserRolesStatistic();

    ResponseDTO getProjectActivityForAdminPerWeek();

    List<UserProjectsDTO> getProjectsForUser(int id);

    List<Integer> getAllToWeekProject();

    int getCountArchiveProject();

    int getCountLastDayCreatedProject();

    List<PlannedTestCaseDTO> getPlannedTestCasesForManager();

    List<PlannedTestCaseDTO> getPlannedTestCasesForEngineer(int id);

    List<SuppProjectsDTO> getSuppProjects();

    List<Integer> getTestCaseStatistic(int projectId);
}
