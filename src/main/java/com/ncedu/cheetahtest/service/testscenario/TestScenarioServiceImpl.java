package com.ncedu.cheetahtest.service.testscenario;

import com.ncedu.cheetahtest.dao.actscenario.ActScenarioDao;
import com.ncedu.cheetahtest.dao.genericdao.AbstractActiveDao;
import com.ncedu.cheetahtest.dao.testscenario.TestScenarioDao;
import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.actscenario.ActScenario;
import com.ncedu.cheetahtest.entity.testcase.TestCase;
import com.ncedu.cheetahtest.entity.testscenario.ActionsAndCompoundsID;
import com.ncedu.cheetahtest.entity.testscenario.PaginationItems;
import com.ncedu.cheetahtest.entity.testscenario.PaginationTestScenario;
import com.ncedu.cheetahtest.entity.testscenario.TestScenario;
import com.ncedu.cheetahtest.exception.testcase.TestCaseNotFoundException;
import com.ncedu.cheetahtest.exception.testscenario.TestScenarioAlreadyExistsException;
import com.ncedu.cheetahtest.exception.testscenario.TestScenarioNotFoundException;
import com.ncedu.cheetahtest.service.action.ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ncedu.cheetahtest.entity.testscenario.StatusTestScenario.ACTIVE;

@Service
@RequiredArgsConstructor
public class TestScenarioServiceImpl implements TestScenarioService {

    private final TestScenarioDao testScenarioDao;
    private final AbstractActiveDao<TestScenario> testScenarioGenDao;
    private final AbstractActiveDao<TestCase> testCaseGenDao;
    private final ActScenarioDao actScenarioDao;
    private final ActionService actionService;

    @Override
    @Transactional
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
    @Transactional
    public TestScenario createTestScenario(TestScenario testScenario, List<ActionsAndCompoundsID> actAndCompID) {
        TestScenario createdTestScen;
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

        testScenario.setStatus(ACTIVE);
        createdTestScen = testScenarioDao.createTestScenario(testScenario);

        ActScenario actScenario = new ActScenario();

        int priority = 1;
        for (ActionsAndCompoundsID actAndComp: actAndCompID) {
            if (actAndComp.getKind().equals("ACTION")){
                actScenario.setActionId(actAndComp.getId());
                actScenario.setTestScenarioId(createdTestScen.getId());
                actScenario.setPriority(priority);
                actScenarioDao.createActScenario(actScenario);
            } else if (actAndComp.getKind().equals("COMPOUND")){
                List<Action> actions = actionService.getActionsInCompound(actAndComp.getId());
                for(Action action: actions){
                    actScenario.setActionId(action.getId());
                    actScenario.setTestScenarioId(createdTestScen.getId());
                    actScenario.setPriority(priority);
                    actScenarioDao.createActScenario(actScenario);
                    priority++;
                }
            }
            priority++;
        }

        return createdTestScen;
    }


    @Override
    @Transactional
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
    @Transactional
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
    @Transactional
    public TestScenario editTestScenario(TestScenario testScenario, int id) {
        TestScenario testScenarioWithExistTitle = testScenarioDao
                .findTestScenarioByTitleExceptCurrent(testScenario.getTitle(),id);
        if(testScenarioWithExistTitle == null){
            return testScenarioDao.editTestScenario(testScenario, id);
        } else {
            throw  new TestScenarioAlreadyExistsException();
        }

    }

    @Override
    @Transactional
    public PaginationTestScenario getAllTestScenarios(int size, int page) {
        List<TestScenario> testScenarioList = testScenarioGenDao.getAllPaginated(page, size);
        int totalElements = testScenarioGenDao.getAmountAllElements();

        PaginationTestScenario paginationTestScenario = new PaginationTestScenario();
        paginationTestScenario.setTotalElements(totalElements);
        paginationTestScenario.setTestScenarios(testScenarioList);

        return paginationTestScenario;
    }

    @Override
    @Transactional
    public void deactivateTestScenario(int id) {
        testScenarioDao.deactivate(id);
    }

    @Override
    @Transactional
    public TestScenario findById(int id) {
        TestScenario testScenario = testScenarioGenDao.findById(id);

        if (testScenario == null) {
            throw new TestScenarioNotFoundException();
        }

        return testScenario;
    }
}
