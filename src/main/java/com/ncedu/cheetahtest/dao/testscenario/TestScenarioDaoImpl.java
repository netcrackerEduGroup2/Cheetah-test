package com.ncedu.cheetahtest.dao.testscenario;

import com.ncedu.cheetahtest.entity.testscenario.TestScenario;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestScenarioDaoImpl implements TestScenarioDao{
    @Override
    public TestScenario findById(int id) { //TODO
        return null;
    }

    @Override
    public List<TestScenario> findByTitleLike(String title, int idTestCase, int limit, int offset) { //TODO
        return null;
    }

    @Override
    public TestScenario createTestScenario(TestScenario testScenario) { //TODO
        return null;
    } //TODO

    @Override
    public TestScenario editTestScenario(TestScenario testScenario, int id) { //TODO
        return null;
    } //TODO

    @Override
    public void deleteTestScenario(int id) { //TODO

    }

    @Override
    public int getTotalElements(int idTestcase, String title) { //TODO
        return 0;
    } //TODO

    @Override
    public TestScenario findByTitle(String title) { //TODO
        return null;
    }
}
