package com.ncedu.cheetahtest.service.testcase.runwrapper;

import com.ncedu.cheetahtest.entity.generalentity.IdsDto;

public interface TestCaseLauncher {
    void formActionForSelenium(int testCaseId);

    void runTestCases(IdsDto idsDto);
}
