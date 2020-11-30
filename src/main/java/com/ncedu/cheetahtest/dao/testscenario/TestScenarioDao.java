package com.ncedu.cheetahtest.dao.testscenario;

import com.ncedu.cheetahtest.entity.testscenario.TestScenario;

import java.util.List;

public interface TestScenarioDao {
    TestScenario findById(int id);

    List<TestScenario> findByTitleLike(String title, int idTestCase, int limit, int offset);

    TestScenario createTestScenario(TestScenario testScenario);

    TestScenario editTestScenario(TestScenario testScenario, int id);

    List<TestScenario> findTestScenariosFromTestCase(int idTestCase, int limit, int offset);

    void deleteTestScenario(int id);

    int getTotalElements(int idTestcase, String title);

    int getTestScenariosFromTestCaseAmount(int idTestCase);

    TestScenario findByTitle(String title);

    List<TestScenario> getAllPaginated(int page, int size);

    int getAmountAllElements();

    void deactivate(int id);

    TestScenario findTestScenarioByTitleExceptCurrent(String title, int id);
}
