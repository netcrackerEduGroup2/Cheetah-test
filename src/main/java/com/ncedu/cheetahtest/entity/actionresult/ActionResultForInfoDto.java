package com.ncedu.cheetahtest.entity.actionresult;

import com.ncedu.cheetahtest.entity.selenium.ActionResultStatus;
import lombok.Data;

@Data
public class ActionResultForInfoDto {
    private int id;
    private String actionType;
    private ActionResultStatus status;
    private String resultDescription;
    private String screenshotUrl;
    private String element;
    private String argument;
}
