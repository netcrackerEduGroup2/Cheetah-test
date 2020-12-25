package com.ncedu.cheetahtest.service.dashboard;

import com.ncedu.cheetahtest.dao.dashboard.DashboardDao;
import com.ncedu.cheetahtest.entity.dashboard.*;
import com.ncedu.cheetahtest.entity.user.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final DashboardDao dashboardDao;
    private static final String ONE_DAY_INTERVAL = "1";
    private static final String ONE_WEEK_INTERVAL_HOURS = "168";
    private static final String USER_DATE_PATTERN = "HH:mm";
    private static final String USER_DATE_PATTERN_WEEK = "dd.MM 'at' HH:mm";
    private static final String TEST_CASE_STATUS_COMPLETE = "COMPLETE";
    private static final String TEST_CASE_STATUS_FAILED = "FAILED";

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
        for (UserRole role : UserRole.values()) {
            roles.add(dashboardDao.getCountActiveUserByRole(role.toString()));
        }
        return roles;
    }

    @Override
    public ResponseDTO getProjectActivityForAdminPerWeek() {
        List<ProjectActivityDTO> projects = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        for (int i = 6; i > 0; i--) {
            String from = String.valueOf(i * 24 + hour);
            String to = String.valueOf((i - 1) * 24 + hour);
            int count = dashboardDao.getProjectActivitiesPerDayOnWeek(from, to);

            String dayOfMonthStr = (dayOfMonth - i) + "." + month;
            projects.add(new ProjectActivityDTO(dayOfMonthStr, count));
        }
        int count = dashboardDao.getProjectActivitiesPerDayOnWeek(String.valueOf(hour), "0");
        String dayOfMonthStr = dayOfMonth + "." + month;
        projects.add(new ProjectActivityDTO(dayOfMonthStr, count));

        return new ResponseDTO(projects);
    }

    @Override
    public List<PlannedTestCaseDTO> getPlannedTestCasesForManager() {
        List<PlannedTestCaseDTO> plannedTestCaseDTOS = dashboardDao.getPlannedTestCasesForManager();
        plannedTestCaseDTOS.forEach(tc -> tc.setCronDate(parseToStringDate(tc.getCronDate())));

        return plannedTestCaseDTOS;
    }

    @Override
    public List<PlannedTestCaseDTO> getPlannedTestCasesForEngineer(int id) {
        List<PlannedTestCaseDTO> plannedTestCaseDTOS = dashboardDao.getPlannedTestCasesForEngineer(id);
        plannedTestCaseDTOS.forEach(tc -> tc.setCronDate(parseToStringDate(tc.getCronDate())));

        return plannedTestCaseDTOS;
    }

    @Override
    public List<SuppProjectsDTO> getSuppProjects() {
        return dashboardDao.getSuppProjects();
    }

    @Override
    public List<Integer> getTestCaseStatistic(int projectId) {
        List<Integer> testCases = new ArrayList<>();
        int completed = dashboardDao.getCountTestedCasesByProject(projectId, TEST_CASE_STATUS_COMPLETE);
        int failed = dashboardDao.getCountTestedCasesByProject(projectId, TEST_CASE_STATUS_FAILED);
        int planned = dashboardDao.getCountPlannedCasesByProject(projectId);

        testCases.add(completed);
        testCases.add(failed);
        testCases.add(planned);
        testCases.add(completed + failed + planned);

        return testCases;
    }

    @Override
    public List<UserProjectsDTO> getProjectsForUser(int id) {

        return dashboardDao.getProjectsForUser(id);
    }

    @Override
    public List<Integer> getAllToWeekProject() {
        List<Integer> projects = new ArrayList<>();

        projects.add(dashboardDao.getCountAllProject());
        projects.add(dashboardDao.getCountWeekProject(ONE_WEEK_INTERVAL_HOURS));

        return projects;
    }

    @Override
    public int getCountArchiveProject() {
        return dashboardDao.getCountArchiveProjects();
    }

    @Override
    public int getCountLastDayCreatedProject() {
        return dashboardDao.getCountLastTimeCreatedProject(ONE_DAY_INTERVAL);
    }

    private static String parseToStringDate(String cron) {
        String minutes = cron.substring(3, 5);
        String hour = cron.substring(6, 8);
        String day = cron.substring(9, 11);
        String month = cron.substring(12, 14);
        return String.format("%s.%s.%s at %s:%s", day, month, LocalDate.now().getYear(), hour, minutes);
    }

}
