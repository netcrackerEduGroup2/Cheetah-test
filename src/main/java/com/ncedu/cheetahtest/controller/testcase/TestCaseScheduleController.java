package com.ncedu.cheetahtest.controller.testcase;

import com.ncedu.cheetahtest.entity.testcase.TestCase;
import com.ncedu.cheetahtest.entity.testcase.TestCaseScheduleDto;
import com.ncedu.cheetahtest.exception.testcase.InvalidCronExpressionException;
import com.ncedu.cheetahtest.service.testcase.crud.TestCaseService;
import com.ncedu.cheetahtest.service.testcase.scheduling.TestCaseScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
        List<TestCase> testCases = testCaseService.getActiveTestCasesWithExecutionDate();
        for (TestCase testCase: testCases){
            testCase.setExecutionCronDate(parseToDate(testCase.getExecutionCronDate()));
        }
        return testCases;
    }

    @PostMapping
    public ResponseEntity<String> createTestCaseSchedule(@RequestBody TestCaseScheduleDto testCaseScheduleDto) {

        testCaseScheduleDto.setExecutionCronDate(parseToCron(testCaseScheduleDto.getExecutionCronDate()));
        testCaseService.updateExecutionCronDateAndRepeatability(testCaseScheduleDto);
        testCaseScheduler.createTestCaseSchedule(testCaseScheduleDto.getTestCaseId());
        boolean isValidCron = CronExpression.isValidExpression(testCaseScheduleDto.getExecutionCronDate());

        if (!isValidCron) {
            throw new InvalidCronExpressionException();
        }

        testCaseScheduler.createTestCaseSchedule(testCaseScheduleDto);

        String response = "Test case has been scheduled. Id = " + testCaseScheduleDto.getTestCaseId();

        log.info(response);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<String> updateTestCaseSchedule(@RequestBody TestCaseScheduleDto testCaseScheduleDto) {

        testCaseScheduleDto.setExecutionCronDate(parseToCron(testCaseScheduleDto.getExecutionCronDate()));
        testCaseService.updateExecutionCronDateAndRepeatability(testCaseScheduleDto);
        testCaseScheduler.updateTestCaseSchedule(testCaseScheduleDto.getTestCaseId());
        boolean isValidCron = CronExpression.isValidExpression(testCaseScheduleDto.getExecutionCronDate());

        if (!isValidCron) {
            throw new InvalidCronExpressionException();
        }

        testCaseScheduler.updateTestCaseSchedule(testCaseScheduleDto);

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

    @GetMapping("/test-cases")
    public List<TestCase> getCalendarTestCases(@RequestParam("title") String title)   {
        List<TestCase> testCases = testCaseService.getAllActiveTestCasesByTitle(title);
        for (TestCase testCase: testCases){
            if(testCase.getExecutionCronDate() != null)
                testCase.setExecutionCronDate(parseToDate(testCase.getExecutionCronDate()));
        }
        return testCases;
    }

    private static String parseToCron(String date){
        String month = date.substring(date.indexOf("-") + 1, date.indexOf("-") + 3);
        String day = date.substring(date.indexOf("-") + 4, date.indexOf("-") + 6);
        String hour = date.substring(date.indexOf("T") + 1, date.indexOf(":"));
        String minutes = date.substring(date.indexOf(":") + 1, date.indexOf(":") + 3);
        return String.format("00 %s %s %s %s ?", minutes, hour, day, month);
    }

    private static String parseToDate(String cron){
        String minutes = cron.substring(3, 5);
        String hour = cron.substring(6, 8);
        String day = cron.substring(9, 11);
        String month = cron.substring(12, 14);
        return String.format("%s-%s-%sT%s:%s:00+00:00", LocalDate.now().getYear(), month, day, hour, minutes);
    }
}
