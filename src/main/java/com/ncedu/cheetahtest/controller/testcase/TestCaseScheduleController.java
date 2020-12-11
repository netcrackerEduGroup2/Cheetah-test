package com.ncedu.cheetahtest.controller.testcase;

import com.ncedu.cheetahtest.entity.testcase.TestCase;
import com.ncedu.cheetahtest.entity.testcase.TestCaseScheduleDto;
import com.ncedu.cheetahtest.service.testcase.crud.TestCaseService;
import com.ncedu.cheetahtest.service.testcase.scheduling.TestCaseScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule-test-case")
@CrossOrigin(origins = "${frontend.ulr}")
@RequiredArgsConstructor
@Slf4j
public class TestCaseScheduleController {

    private final TestCaseService testCaseService;
    private final TestCaseScheduler testCaseScheduler;

    @GetMapping
    public List<TestCase> createTestCaseSchedule() {
        return testCaseService.getActiveTestCasesWithExecutionDate();
    }

    @PostMapping
    public ResponseEntity<String> createTestCaseSchedule(@RequestBody TestCaseScheduleDto testCaseScheduleDto) {

        testCaseService.updateExecutionCronDateAndRepeatability(testCaseScheduleDto);

        testCaseScheduler.createTestCaseSchedule(testCaseScheduleDto.getTestCaseId());

        String response = "Test case has been scheduled. Id = " + testCaseScheduleDto.getTestCaseId();

        log.info(response);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<String> updateTestCaseSchedule(@RequestBody TestCaseScheduleDto testCaseScheduleDto) {

        testCaseService.updateExecutionCronDateAndRepeatability(testCaseScheduleDto);

        testCaseScheduler.updateTestCaseSchedule(testCaseScheduleDto.getTestCaseId());

        String response = "Test case schedule has been updated. Id = " + testCaseScheduleDto.getTestCaseId();

        log.info(response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{testCaseId}")
    public ResponseEntity<String> updateTestCaseSchedule(@PathVariable int testCaseId) {

        testCaseService.deleteExecutionCronDateAndRepeatability(testCaseId);

        testCaseScheduler.deleteTestCaseSchedule(testCaseId);

        String response = "Test case schedule has been deleted. Id = " + testCaseId;

        log.info(response);
        return ResponseEntity.ok(response);
    }
}
