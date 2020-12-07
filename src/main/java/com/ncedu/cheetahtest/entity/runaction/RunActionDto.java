package com.ncedu.cheetahtest.entity.runaction;

import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;

public class RunActionDto {
    private int id;
    private int compoundId;
    private TestCaseResult result;
    private String screenshotURL;
    private String actionType;
    private String element;
    private String argument;

    public RunActionDto(int id, int compoundId, TestCaseResult result,
                        String screenshotURL, String actionType, String element, String argument) {
        this.id = id;
        this.compoundId = compoundId;
        this.result = result;
        this.screenshotURL = screenshotURL;
        this.actionType = actionType;
        this.element = element;
        this.argument = argument;
    }
}
