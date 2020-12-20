package com.ncedu.cheetahtest.service.dashboard;

import com.ncedu.cheetahtest.entity.dashboard.UserActivityDTO;

import java.util.*;

public interface DashboardService {

    List<UserActivityDTO> getActiveUsersForAdminPerDay();

    List<UserActivityDTO> getActiveUsersForManagerPerDay();

    List<Integer> getUserRolesStatistic();
}
