package com.ncedu.cheetahtest.service.selenium;

import com.ncedu.cheetahtest.entity.selenium.ActionResult;
import com.ncedu.cheetahtest.entity.selenium.ActionResultStatus;
import com.ncedu.cheetahtest.entity.selenium.SeleniumAction;
import org.openqa.selenium.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ActionExecutorProviderImpl implements ActionExecutorProvider {

    private Map<String, ActionExecutor> actionMap;

    public ActionExecutorProviderImpl() {
        actionMap = new HashMap<>();

        actionMap.put("getPage", this::getPage);
        actionMap.put("checkTitle", this::checkTitle);

    }

    @Override
    public ActionExecutor getActionExecutor(String actionType) {
        return actionMap.get(actionType);
    }

    private ActionResult getPage(SeleniumAction action, WebDriver webDriver) {
        ActionResult actionResult = new ActionResult();
        actionResult.setAction(action);

        try {
            webDriver.get(action.getArgument());
        } catch (WebDriverException e) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("This ULR can’t be reached");

            return actionResult;
        }

        actionResult.setStatus(ActionResultStatus.SUCCESS);
        actionResult.setResultDescription("Action was successfully executed");

        return actionResult;
    }

    private ActionResult checkTitle(SeleniumAction action, WebDriver webDriver) {
        ActionResult actionResult = new ActionResult();
        actionResult.setAction(action);

        if (webDriver.getTitle().equals(action.getArgument())) {
            actionResult.setStatus(ActionResultStatus.SUCCESS);
            actionResult.setResultDescription("Action was successfully executed");
        } else {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("The titles aren't matched");
        }

        return actionResult;
    }

    private ActionResult checkUrl(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult goBack(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult goForward(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult hoverElementById(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult hoverElementByClassName(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult hoverElementByCssSelector(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult checkElementById(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult checkElementByClassName(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult checkElementByCssSelector(SeleniumAction action, WebDriver webDriver) {
        ActionResult actionResult = new ActionResult();
        actionResult.setAction(action);

        try {
            WebElement webElement = webDriver.findElement(By.cssSelector(action.getElement()));

            return checkElement(action, webElement);
        } catch (WebDriverException e) {
            actionResult.setResultDescription("Fail");
            actionResult.setStatus(ActionResultStatus.FAIL);

            return actionResult;
        }

    }

    private ActionResult checkElement(SeleniumAction action, WebElement webElement) {
        ActionResult actionResult = new ActionResult();

        actionResult.setAction(action);
        actionResult.setResultDescription("Success");
        actionResult.setStatus(ActionResultStatus.SUCCESS);

        return actionResult;
    }

    private ActionResult pressEnter(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult clickElementById(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult clickElementByClassName(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult clickElementByCssSelector(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult fillById(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult fillByClassname(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult fillByCssSelector(SeleniumAction action, WebDriver webDriver) {
        return null;
    }
}
