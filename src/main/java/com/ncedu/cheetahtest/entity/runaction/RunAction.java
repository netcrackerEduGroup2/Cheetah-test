package com.ncedu.cheetahtest.entity.runaction;

import lombok.Data;
//dto run
@Data
public class RunAction {
    private int id;
    private int compoundId;
    private String result;
    private String screenshotURL;
    private String actionType;
    private String element;
    private String argument;
}
