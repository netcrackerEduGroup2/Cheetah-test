package com.ncedu.cheetahtest.service.testcase.runwrapper;

import com.ncedu.cheetahtest.entity.selenium.ActionResult;
import com.ncedu.cheetahtest.entity.selenium.SeleniumAction;

import java.util.List;

public interface TestCaseLauncher {
    void formActionForSelenium(int testCaseId);

    List<ActionResult> processActions(List<SeleniumAction> actionList, int testCaseId);
}
