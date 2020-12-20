package com.ncedu.cheetahtest.service.dashboard;

import com.ncedu.cheetahtest.dao.dashboard.DashboardDao;
import com.ncedu.cheetahtest.entity.dashboard.UserActivityDTO;
import com.ncedu.cheetahtest.entity.user.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService{
    private final DashboardDao dashboardDao;
    private static final String ONE_DAY_INTERVAL = "1";
    private static final String ONE_WEEK_INTERVAL = "7";
    private static final String USER_DATE_PATTERN = "HH:mm";

    @Override
    public List<UserActivityDTO> getActiveUsersForAdminPerDay() {

        List<UserActivityDTO> usersForAdmin = dashboardDao.getActiveUsersForAdminPerDays(ONE_DAY_INTERVAL);
        usersForAdmin.forEach(u -> u.setTime(new SimpleDateFormat(USER_DATE_PATTERN)
                        .format(Timestamp.valueOf(u.getTime()))));

        return usersForAdmin;
    }

    @Override
    public List<UserActivityDTO> getActiveUsersForManagerPerDay() {
        List<UserActivityDTO> usersForManager = dashboardDao.getActiveUsersForManagerPerDays(ONE_DAY_INTERVAL);
        usersForManager.forEach(u -> u.setTime(new SimpleDateFormat(USER_DATE_PATTERN)
                .format(Timestamp.valueOf(u.getTime()))));

        return usersForManager;
    }

    @Override
    public List<Integer> getUserRolesStatistic() {
        List<Integer> roles = new ArrayList<>();
        for(UserRole role: UserRole.values()){
            roles.add(dashboardDao.getCountActiveUserByRole(role.toString()));
        }
        return roles;
    }

}
