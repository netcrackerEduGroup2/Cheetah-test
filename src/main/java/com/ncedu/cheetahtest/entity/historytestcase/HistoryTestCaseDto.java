package com.ncedu.cheetahtest.entity.historytestcase;

import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;
import lombok.Data;

import java.util.Date;

@Data
public class HistoryTestCaseDto {
    private int id;
    private TestCaseResult result;
    private Date dataCompleted;
    private String title;

    public HistoryTestCaseDto(int id, TestCaseResult result, Date dataCompleted, String title) {
        this.id = id;
        this.result = result;
        this.dataCompleted = dataCompleted;
        this.title = title;
    }
}
