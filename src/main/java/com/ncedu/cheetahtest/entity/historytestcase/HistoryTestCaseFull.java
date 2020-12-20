package com.ncedu.cheetahtest.entity.historytestcase;

import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;
import lombok.Data;

import java.util.Date;
@Data
public class HistoryTestCaseFull {
    private int id;
    private TestCaseResult result;
    private Date dataCompleted;
    private int idTestCase;
}
