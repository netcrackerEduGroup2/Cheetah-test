package com.ncedu.cheetahtest.service.testcase.runwrapper;

import com.ncedu.cheetahtest.dao.actscenario.ActScenarioDao;
import com.ncedu.cheetahtest.dao.compound.CompoundDao;
import com.ncedu.cheetahtest.dao.compscenario.CompScenarioDao;
import com.ncedu.cheetahtest.entity.actscenario.ActScenario;
import com.ncedu.cheetahtest.entity.compscenario.CompScenario;
import com.ncedu.cheetahtest.entity.scenario.Scenario;
import com.ncedu.cheetahtest.entity.selenium.ActionResult;
import com.ncedu.cheetahtest.entity.selenium.ActionResultStatus;
import com.ncedu.cheetahtest.entity.selenium.SeleniumAction;
import com.ncedu.cheetahtest.service.compscenario.CompScenarioService;
import com.ncedu.cheetahtest.service.selenium.TestCaseExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestCaseLauncherImpl implements TestCaseLauncher {

    private final ActScenarioDao actScenarioDao;
    private final CompoundDao compoundDao;
    private final CompScenarioDao compScenarioDao;
    private final CompScenarioService compScenarioService;
    private final TestCaseExecutor testCaseExecutor;

    @Override
    @Transactional
    public void formActionForSelenium(int testCaseId) {
        List<ActScenario> actScenarios = actScenarioDao.getAllByTestCaseId(testCaseId);
        List<CompScenario> compScenarios = compScenarioDao.getAllByTestCaseId(testCaseId);

        System.out.println("---------------ACT SCENARIO--------------");
        actScenarios.forEach(actScenario -> System.out.println(actScenario + "\n"));

        System.out.println("---------------COMP SCENARIO--------------");
        compScenarios.forEach(compScenario -> System.out.println(compScenario + "\n"));

        System.out.println("---------------TOGETHER SORTED--------------");
        List<Scenario> actionsAndCompounds = new ArrayList<>();
        actionsAndCompounds.addAll(actScenarios);
        actionsAndCompounds.addAll(compScenarios);
        actionsAndCompounds.sort(Comparator.comparingInt(Scenario::getPriority));
        actionsAndCompounds.forEach(scenario -> System.out.println(scenario + "\n"));

        System.out.println("---------------ACTIONS AND COMPOUNDS UNPACKED SORTED--------------");
        List<ActScenario> actScenariosOfActAndComps = makeActionScenarioListOfScenario(actionsAndCompounds);

        List<SeleniumAction> seleniumActions = mapActScenarioToSeleniumAction(actScenariosOfActAndComps);
        List<ActionResult> actionResults = processActions(seleniumActions);
        actionResults.forEach(actionResult -> System.out.println(actionResult + "\n"));
    }

    private List<ActScenario> makeActionScenarioListOfScenario(List<Scenario> actionsAndCompounds) {
        List<ActScenario> actScenarios = new ArrayList<>(actionsAndCompounds.size());

        actionsAndCompounds.forEach(scenario -> {
            if (scenario instanceof ActScenario) {
                actScenarios.add((ActScenario) scenario);
            } else {
                actScenarios.addAll(
                        compScenarioService.getAllActionScenarioInComp(
                                ((CompScenario) scenario).getId()
                        ));
            }
        });

        return actScenarios;
    }

    @Transactional
    public List<SeleniumAction> mapActScenarioToSeleniumAction(
            List<ActScenario> actScenarios) {

        List<SeleniumAction> seleniumActions = new ArrayList<>();

        actScenarios.forEach((actScenario -> {
            Integer compId = compoundDao.getCompoundIdByActionId(actScenario.getActionId());
            seleniumActions.add(new SeleniumAction(
                    compId,
                    actScenario.getActionType(),
                    actScenario.getParameterType(),
                    actScenario.getParameterValue()));
        }));

        return seleniumActions;
    }

    private List<ActionResult> processActions(List<SeleniumAction> actionList) {
        List<ActionResult> actionResults = new ArrayList<>();

        for (SeleniumAction seleniumAction : actionList) {
            ActionResult theActionResult = testCaseExecutor.executeAction(seleniumAction);
            actionResults.add(theActionResult);

            // TODO add actionResult to db

            if (theActionResult.getStatus().equals(ActionResultStatus.FAIL)) {
                testCaseExecutor.close();
                break;
            }
        }

        testCaseExecutor.close();
        return actionResults;
    }
}
