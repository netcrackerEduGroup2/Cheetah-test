package com.ncedu.cheetahtest.dao.testscenario;

import com.ncedu.cheetahtest.entity.dataset.DataSet;
import com.ncedu.cheetahtest.entity.testscenario.TestScenario;

import java.util.List;

public interface TestScenarioDao {
    TestScenario findById(int id);
    List<TestScenario> findByTitleLike(String title, int idTestCase, int limit, int offset);
    TestScenario createTestScenario(TestScenario testScenario);
    TestScenario editTestScenario(TestScenario testScenario, int id);
    void deleteTestScenario(int id);
    int getTotalElements(int idTestcase,String title);
    TestScenario findByTitle(String title);
}
