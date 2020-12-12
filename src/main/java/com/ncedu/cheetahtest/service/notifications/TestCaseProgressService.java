package com.ncedu.cheetahtest.service.notifications;

import com.ncedu.cheetahtest.entity.selenium.ActionResult;

import java.util.List;

public interface TestCaseProgressService {
    void calculateAndSendProgress(int idTestCase, int totalActionResults, List<ActionResult> completed);
}
