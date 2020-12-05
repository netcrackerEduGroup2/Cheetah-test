package com.ncedu.cheetahtest.entity.action;

import lombok.Data;
import lombok.NonNull;

@Data
public class ActionResult {

    @NonNull private SeleniumAction action;
    @NonNull private ActionStatus status;
    @NonNull private String resultDescription;
    @NonNull private String screenshotUrl;

}
