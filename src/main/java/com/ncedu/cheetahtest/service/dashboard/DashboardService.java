package com.ncedu.cheetahtest.service.dashboard;

import com.ncedu.cheetahtest.entity.dashboard.*;

import java.util.*;

public interface DashboardService {

    List<UserActivityDTO> getActiveUsersForAdminPerDay();

    List<UserActivityDTO> getActiveUsersForManagerPerDay();

    List<Integer> getUserRolesStatistic();

    List<ProjectActivityDTO> getProjectActivityForAdminPerWeek();

    List<UserProjectsDTO> getProjectsForUser(int id);

    List<Integer> getAllToWeekProject();

    int getCountArchiveProject();

    int getCountLastDayCreatedProject();

    List<PlannedTestCaseDTO> getPlannedTestCasesForManager();

    List<PlannedTestCaseDTO> getPlannedTestCasesForEngineer(int id);
}
