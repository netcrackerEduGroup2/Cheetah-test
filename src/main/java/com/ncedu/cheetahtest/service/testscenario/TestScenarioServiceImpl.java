package com.ncedu.cheetahtest.service.testscenario;

import com.ncedu.cheetahtest.dao.testscenario.TestScenarioDao;
import com.ncedu.cheetahtest.entity.testscenario.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestScenarioServiceImpl implements TestScenarioService{
    private final TestScenarioDao testScenarioDao;

    @Autowired
    public TestScenarioServiceImpl(TestScenarioDao testScenarioDao) {
        this.testScenarioDao = testScenarioDao;
    }

    @Override
    public PaginationTestScenario findByTitle(String title, int idTestCase, int size, int page) { //TODO
        return null;
    } //TODO

    @Override
    public TestScenario createTestScenario(TestScenario testScenario) { //TODO
        return null;
    } //TODO

    @Override
    public PaginationItems getItemsFromScenario(int idTestScenario, int size, int page) { //TODO
        return null;
    } //TODO

    @Override
    public PaginationTestScenario getAllTestScenariosFromTestCase(int idTestCase, int size, int page) { //TODO
        return null;
    }

    @Override
    public TestScenario editTestScenario(TestScenario testScenario, int id) { //TODO
        return null;
    } //TODO

    @Override
    public PaginationTestScenario getAllTestScenarios( int size, int page) { //TODO
        return null;
    } //TODO

    @Override
    public void deleteTestScenario(int id) { //TODO

    }
}
