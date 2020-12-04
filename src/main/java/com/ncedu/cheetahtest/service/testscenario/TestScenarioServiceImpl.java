package com.ncedu.cheetahtest.service.testscenario;

import com.ncedu.cheetahtest.dao.genericdao.AbstractActiveDao;
import com.ncedu.cheetahtest.dao.testcase.TestCaseDao;
import com.ncedu.cheetahtest.dao.testscenario.TestScenarioDao;
import com.ncedu.cheetahtest.entity.testcase.TestCase;
import com.ncedu.cheetahtest.entity.testscenario.*;
import com.ncedu.cheetahtest.exception.testcase.TestCaseNotFoundException;
import com.ncedu.cheetahtest.exception.testscenario.TestScenarioAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestScenarioServiceImpl implements TestScenarioService {

    private final TestScenarioDao testScenarioDao;
    private final AbstractActiveDao<TestCase> testCaseGenDao;
    private final TestCaseDao testCaseDao;

    @Override
    public PaginationTestScenario findByTitle(String title, int idTestCase, int size, int page) {
        int totalTestScenarios = testScenarioDao.getTotalElements(idTestCase, title);
        PaginationTestScenario paginationTestScenario = new PaginationTestScenario();
        paginationTestScenario.setTotalElements(totalTestScenarios);
        if (size * (page - 1) < totalTestScenarios) {
            paginationTestScenario.setTestScenarios(testScenarioDao.findByTitleLike(title, idTestCase, size, size * (page - 1)));
        }
        return paginationTestScenario;
    }

    @Override
    public TestScenario createTestScenario(TestScenario testScenario) {
        TestScenario testScenarioWithSameTitle = testScenarioDao
                .findTestScenarioByTitleExceptCurrent(
                        testScenario.getTitle(),
                        testScenario.getId());

        TestCase testCase = testCaseGenDao.findById(testScenario.getIdTestCase());

        if (testCase == null) {
            throw new TestCaseNotFoundException();
        }
        if (testScenarioWithSameTitle != null) {
            throw new TestScenarioAlreadyExistsException();
        }

        return testScenarioDao.createTestScenario(testScenario);
    }


    @Override
    public PaginationItems getItemsFromScenario(int idTestScenario, int size, int page) {
        int totalElements = testScenarioDao.getAllItemsAmount(idTestScenario);
        PaginationItems paginationItems = new PaginationItems();
        paginationItems.setTotalElements(totalElements);
        if (size * (page - 1) < totalElements) {
            paginationItems.setItemsFromTestScenario(testScenarioDao.getAllItems(idTestScenario, size, size * (page - 1)));
        }
        return paginationItems;
    }

    @Override
    public PaginationTestScenario getAllTestScenariosFromTestCase(int idTestCase, int size, int page) {
        int totalElements = testScenarioDao.getTestScenariosFromTestCaseAmount(idTestCase);
        PaginationTestScenario paginationTestScenario = new PaginationTestScenario();
        paginationTestScenario.setTotalElements(totalElements);
        if (size * (page - 1) < totalElements) {
            paginationTestScenario.setTestScenarios(testScenarioDao.findTestScenariosFromTestCase(idTestCase, size, size * (page - 1)));
        }
        return paginationTestScenario;
    }

    @Override
    public TestScenario editTestScenario(TestScenario testScenario, int id) {
        return testScenarioDao.editTestScenario(testScenario, id);
    }

    @Override
    public PaginationTestScenario getAllTestScenarios(int size, int page) {
        int totalElements = testScenarioDao.getAmountAllElements();
        PaginationTestScenario paginationTestScenario = new PaginationTestScenario();
        paginationTestScenario.setTotalElements(totalElements);
        if (size * (page - 1) < totalElements) {
            paginationTestScenario.setTestScenarios(testScenarioDao.getAllPaginated(size, size * (page - 1)));
        }
        return paginationTestScenario;
    }

    @Override
    public void deactivateTestScenario(int id) {
        testScenarioDao.deactivate(id);
    }
}
