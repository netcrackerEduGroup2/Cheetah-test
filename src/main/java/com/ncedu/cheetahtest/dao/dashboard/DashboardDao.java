package com.ncedu.cheetahtest.dao.dashboard;

import com.ncedu.cheetahtest.entity.dashboard.UserActivityDTO;

import java.util.List;

public interface DashboardDao {

    List<UserActivityDTO> getActiveUsersForAdminPerDays(String days);

    List<UserActivityDTO> getActiveUsersForManagerPerDays(String days);

    int getCountActiveUserByRole(String role);
}
