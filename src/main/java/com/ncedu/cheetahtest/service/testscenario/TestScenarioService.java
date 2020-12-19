package com.ncedu.cheetahtest.service.testscenario;

import com.ncedu.cheetahtest.entity.testscenario.ActionsAndCompoundsID;
import com.ncedu.cheetahtest.entity.testscenario.PaginationItems;
import com.ncedu.cheetahtest.entity.testscenario.PaginationTestScenario;
import com.ncedu.cheetahtest.entity.testscenario.TestScenario;

import java.util.List;

public interface TestScenarioService {
    PaginationTestScenario findByTitleAndTestCaseId(String title, int idTestCase, int size, int page);

    PaginationTestScenario findByTitle(String title, int size, int page);

    TestScenario createTestScenario(TestScenario testScenario, List<ActionsAndCompoundsID> actAndCompID);

    PaginationTestScenario getAllTestScenarios(int size, int page);

    PaginationTestScenario getAllTestScenariosFromTestCase(int idTestCase, int size, int page);

    TestScenario editTestScenario(TestScenario testScenario, int id);

    void deactivateTestScenario(int id);

    TestScenario findById(int id);
}
