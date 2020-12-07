package com.ncedu.cheetahtest.entity.history;

import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;
import lombok.Data;

import java.util.Date;

@Data
public class HistoryTestCaseDto {
    private int id;
    private TestCaseResult result;
    private Date dataCompleted;
    private int idTestCase;

    public HistoryTestCaseDto(int id, TestCaseResult result, Date dataCompleted, int idTestCase) {
        this.id = id;
        this.result = result;
        this.dataCompleted = dataCompleted;
        this.idTestCase = idTestCase;
    }
}
