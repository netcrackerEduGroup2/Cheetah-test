package com.ncedu.cheetahtest.entity.notification;

import com.ncedu.cheetahtest.entity.selenium.ActionResult;
import lombok.Data;

import java.util.List;

@Data
public class ProgressNotification {
    private List<ActionResult> actionResults;
    private int totalActionResults;
}
