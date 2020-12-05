package com.ncedu.cheetahtest.service.testcaseexecution;

import com.ncedu.cheetahtest.entity.selenium.ActionResult;
import com.ncedu.cheetahtest.entity.selenium.SeleniumAction;

import java.util.List;

public interface TestCaseExecutor {
    List<ActionResult> execute(List<SeleniumAction> actions);
}
