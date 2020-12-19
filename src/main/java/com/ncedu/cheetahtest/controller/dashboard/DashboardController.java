package com.ncedu.cheetahtest.controller.dashboard;

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
    public List<Integer> getAllTestScenariosFromTestCase() {
        return dashboardService.getUserRolesStatistic();
    }
}
