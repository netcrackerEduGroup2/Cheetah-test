package com.ncedu.cheetahtest.dao.dashboard;

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
}
