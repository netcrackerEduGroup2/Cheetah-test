package com.ncedu.cheetahtest.controller.dashboard;

import com.ncedu.cheetahtest.entity.dashboard.*;
import com.ncedu.cheetahtest.service.dashboard.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "${frontend.ulr}")
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/admin/count-users")
    public List<Integer> getUserRolesStatistic() {
        return dashboardService.getUserRolesStatistic();
    }

    @GetMapping("/admin/users-activity")
    public List<UserActivityDTO> getActiveUsersForAdminPerDay() {
        return dashboardService.getActiveUsersForAdminPerDay();
    }

    @GetMapping("/manager/users-activity")
    public List<UserActivityDTO> getActiveUsersForManagerPerDay() {
        return dashboardService.getActiveUsersForManagerPerDay();
    }

    @GetMapping("/admin/projects-activity")
    public List<ProjectActivityDTO> getProjectActivityForAdminPerWeek() {
        return dashboardService.getProjectActivityForAdminPerWeek();
    }

    @GetMapping("/admin/personal-projects")
    public List<UserProjectsDTO> getProjectsForUser(@RequestParam("id") int id) {
        return dashboardService.getProjectsForUser(id);
    }

    @GetMapping("/admin/percent-projects")
    public List<Integer> getAllToWeekProject() {//todo
        return dashboardService.getAllToWeekProject();
    }

    @GetMapping("/admin/archive-projects")
    public int getArchiveProject() {//todo
        return dashboardService.getCountArchiveProject();
    }

    @GetMapping("/lastday-created-projects")
    public int getCountLastDayCreatedProject() {//todo
        return dashboardService.getCountLastDayCreatedProject();
    }

    @GetMapping("/manager/planned-testcases")
    public List<PlannedTestCaseDTO> getPlannedTestCasesForManager() {//TODO check when not null
        return dashboardService.getPlannedTestCasesForManager();
    }

    @GetMapping("/engineer/planned-testcases")
    public List<PlannedTestCaseDTO> getPlannedTestCasesForEngineer(@RequestParam("id") int id) {//TODO check when not null
        return dashboardService.getPlannedTestCasesForEngineer(id);
    }
}
