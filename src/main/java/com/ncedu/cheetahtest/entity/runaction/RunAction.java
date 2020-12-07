package com.ncedu.cheetahtest.entity.runaction;

import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;
import lombok.Data;

@Data
public class RunAction {
    private int id;
    private int compoundId;
    private TestCaseResult result;
    private String screenshotURL;
    private String actionType;
    private String element;
    private String argument;
}
