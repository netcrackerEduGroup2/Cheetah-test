package com.ncedu.cheetahtest.service.dashboard;

import com.ncedu.cheetahtest.dao.genericdao.AbstractActiveDao;
import com.ncedu.cheetahtest.dao.user.UserDao;
import com.ncedu.cheetahtest.entity.user.User;
import com.ncedu.cheetahtest.entity.user.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService{
    private final UserDao userDao;


    @Override
    public Map<String, Date> getActiveUsersPerWeek() {
        return null;
    }

    @Override
    public Map<String, Date> getActiveUsersPerDay(Date date) {
        return null;
    }

    @Override
    public List<Integer> getUserRolesStatistic() {
        List roles = new ArrayList();
        for(UserRole role: UserRole.values()){
            roles.add(userDao.getCountActiveUserByRole(role.toString()));
        }
        return roles;
    }

}
