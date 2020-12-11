package com.ncedu.cheetahtest.entity.testcase;

import lombok.Data;

@Data
public class TestCaseScheduleDto {
    private int testCaseId;
    private String executionCronDate;
    private boolean repeatable;
}
