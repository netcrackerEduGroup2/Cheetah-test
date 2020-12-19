package com.ncedu.cheetahtest.service.dashboard;

import java.util.*;

public interface DashboardService {
    Map<String, Date> getActiveUsersPerWeek();

    Map<String, Date> getActiveUsersPerDay(Date date);

    List<Integer> getUserRolesStatistic();
}
